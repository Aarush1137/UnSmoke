package com.unsmoke.app.core.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

data class BuddyProfile(
    val uid: String = "",
    val pairingCode: String = "",
    val buddyUids: List<String> = emptyList(),
    val needsHelp: Boolean = false,
    val pendingBuddyRequestUids: List<String> = emptyList(),
    val name: String = "Buddy",
    val quitStartEpochMillis: Long? = null,
    val totalNrtConsumed: Int = 0,
    val expireAt: java.util.Date? = null
)

@Singleton
class BuddyRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val profilesCollection = firestore.collection("profiles")

    var isUsingMockFlow = kotlinx.coroutines.flow.MutableStateFlow(false)
    private var isUsingMock = false
    private val mockProfiles = MutableStateFlow<Map<String, BuddyProfile>>(emptyMap())
    private val mockMyUid = "mock-uid-12345"

    suspend fun signInAnonymously(): String {
        return try {
            var user = auth.currentUser
            if (user == null) {
                val result = auth.signInAnonymously().await()
                user = result.user
            }
            val uid = user?.uid ?: throw Exception("Auth failed")
            
            val doc = profilesCollection.document(uid).get().await()
            val profile = if (doc.exists()) doc.toObject(BuddyProfile::class.java) else null
            if (!doc.exists() || profile?.pairingCode.isNullOrBlank()) {
                val code = (100000..999999).random().toString()
                val initialProfile = (profile ?: BuddyProfile(uid = uid)).copy(uid = uid, pairingCode = code)
                profilesCollection.document(uid).set(initialProfile, SetOptions.merge()).await()
            }
            isUsingMock = false
            isUsingMockFlow.value = false
            uid
        } catch (e: Exception) {
            e.printStackTrace()
            isUsingMock = true
            isUsingMockFlow.value = true
            val currentMap = mockProfiles.value.toMutableMap()
            if (!currentMap.containsKey(mockMyUid)) {
                currentMap[mockMyUid] = BuddyProfile(uid = mockMyUid, pairingCode = "123456")
                mockProfiles.value = currentMap
            }
            mockMyUid
        }
    }

    suspend fun sendBuddyRequest(myUid: String, buddyCode: String): Boolean {
        if (isUsingMock) {
            val targetEntry = mockProfiles.value.entries.find { it.value.pairingCode == buddyCode }
            if (targetEntry == null) return false
            val currentMap = mockProfiles.value.toMutableMap()
            val existing = targetEntry.value.pendingBuddyRequestUids.toMutableList()
            if (!existing.contains(myUid)) existing.add(myUid)
            currentMap[targetEntry.key] = targetEntry.value.copy(pendingBuddyRequestUids = existing)
            mockProfiles.value = currentMap
            return true
        }

        val snapshot = profilesCollection.whereEqualTo("pairingCode", buddyCode).limit(1).get().await()
        if (snapshot.isEmpty) return false
        
        val targetUid = snapshot.documents.first().id
        if (targetUid == myUid) return false // Can't add yourself

        // Write to the target user's buddy_requests sub-collection (allowed by Firestore rules)
        val requestData = mapOf(
            "fromUid" to myUid,
            "timestamp" to System.currentTimeMillis()
        )
        profilesCollection.document(targetUid)
            .collection("buddy_requests")
            .document(myUid)
            .set(requestData)
            .await()

        return true
    }

    suspend fun acceptBuddyRequest(myUid: String, requesterUid: String) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            val requesterProfile = currentMap[requesterUid]
            
            val myNewBuddies = myProfile.buddyUids.toMutableList().apply { if(!contains(requesterUid)) add(requesterUid) }
            val myNewPending = myProfile.pendingBuddyRequestUids.toMutableList().apply { remove(requesterUid) }
            
            currentMap[myUid] = myProfile.copy(buddyUids = myNewBuddies, pendingBuddyRequestUids = myNewPending)
            if (requesterProfile != null) {
                val theirNewBuddies = requesterProfile.buddyUids.toMutableList().apply { if(!contains(myUid)) add(myUid) }
                currentMap[requesterUid] = requesterProfile.copy(buddyUids = theirNewBuddies)
            }
            mockProfiles.value = currentMap
            return
        }

        // Use a batch write: update own profile + write acceptance into requester's sub-collection
        val batch = firestore.batch()

        // 1. Add requester to my buddyUids
        batch.update(profilesCollection.document(myUid),
            "buddyUids", FieldValue.arrayUnion(requesterUid)
        )

        // 2. Remove the request doc from my buddy_requests sub-collection
        batch.delete(
            profilesCollection.document(myUid)
                .collection("buddy_requests")
                .document(requesterUid)
        )

        // 3. Write an acceptance doc into the requester's buddy_requests so their client picks it up
        //    Actually, we write directly to their profile since we need arrayUnion.
        //    Instead, we write an "accepted" doc that their client processes.
        val acceptData = mapOf(
            "fromUid" to myUid,
            "type" to "ACCEPTED",
            "timestamp" to System.currentTimeMillis()
        )
        batch.set(
            profilesCollection.document(requesterUid)
                .collection("buddy_requests")
                .document("accepted_$myUid"),
            acceptData
        )

        batch.commit().await()

        // Also update our own buddyUids (already done in batch) - now process the acceptance on requester side
        // The requester's client will observe their buddy_requests and call processAcceptance
    }

    /** Called by the client when it detects an "ACCEPTED" buddy_request for itself */
    suspend fun processAcceptedRequests(myUid: String) {
        if (isUsingMock) return

        try {
            val acceptedDocs = profilesCollection.document(myUid)
                .collection("buddy_requests")
                .get().await()

            for (doc in acceptedDocs.documents) {
                val type = doc.getString("type")
                val fromUid = doc.getString("fromUid") ?: continue

                if (type == "ACCEPTED") {
                    // Add the accepter to my buddyUids
                    profilesCollection.document(myUid)
                        .update("buddyUids", FieldValue.arrayUnion(fromUid))
                        .await()
                    // Delete the processed acceptance doc
                    doc.reference.delete().await()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateMyStats(myUid: String, name: String, quitStartEpochMillis: Long?, totalNrtConsumed: Int) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            currentMap[myUid] = myProfile.copy(
                name = name, 
                quitStartEpochMillis = quitStartEpochMillis, 
                totalNrtConsumed = totalNrtConsumed
            )
            mockProfiles.value = currentMap
            return
        }
        try {
            profilesCollection.document(myUid).update(
                mapOf(
                    "name" to name,
                    "quitStartEpochMillis" to quitStartEpochMillis,
                    "totalNrtConsumed" to totalNrtConsumed
                )
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun rejectBuddyRequest(myUid: String, requesterUid: String) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            val newPending = myProfile.pendingBuddyRequestUids.toMutableList().apply { remove(requesterUid) }
            currentMap[myUid] = myProfile.copy(pendingBuddyRequestUids = newPending)
            mockProfiles.value = currentMap
            return
        }

        // Delete the request from the buddy_requests sub-collection
        profilesCollection.document(myUid)
            .collection("buddy_requests")
            .document(requesterUid)
            .delete()
            .await()
    }

    fun observeMyProfile(myUid: String): Flow<BuddyProfile?> {
        if (isUsingMock) return mockProfiles.map { it[myUid] }
        return callbackFlow {
            val registration = profilesCollection.document(myUid)
                .addSnapshotListener { snapshot, _ ->
                    val profile = snapshot?.toObject(BuddyProfile::class.java)
                    trySend(profile)
                }
            awaitClose { registration.remove() }
        }
    }

    /** Observe pending buddy requests from the sub-collection */
    fun observePendingRequests(myUid: String): Flow<List<BuddyProfile>> {
        if (isUsingMock) {
            return mockProfiles.map { map ->
                val myProfile = map[myUid] ?: return@map emptyList()
                myProfile.pendingBuddyRequestUids.mapNotNull { map[it] }
            }
        }
        return callbackFlow {
            val registration = profilesCollection.document(myUid)
                .collection("buddy_requests")
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot != null) {
                        val requestUids = snapshot.documents
                            .filter { it.getString("type") != "ACCEPTED" }
                            .mapNotNull { it.getString("fromUid") }
                        
                        val hasAccepted = snapshot.documents.any { it.getString("type") == "ACCEPTED" }
                        if (hasAccepted) {
                            this@callbackFlow.launch(Dispatchers.IO) {
                                processAcceptedRequests(myUid)
                            }
                        }

                        // For each request UID, fetch their profile
                        if (requestUids.isEmpty()) {
                            trySend(emptyList())
                        } else {
                            this@callbackFlow.launch(Dispatchers.IO) {
                                try {
                                    val profiles = fetchProfilesByUids(requestUids)
                                    trySend(profiles)
                                } catch (e: Exception) {
                                    trySend(emptyList())
                                }
                            }
                        }
                    } else {
                        trySend(emptyList())
                    }
                }
            awaitClose { registration.remove() }
        }
    }

    /** Fetch profiles by UIDs with chunking to handle Firestore's 10-item whereIn limit */
    private suspend fun fetchProfilesByUids(uids: List<String>): List<BuddyProfile> {
        if (uids.isEmpty()) return emptyList()
        val results = mutableListOf<BuddyProfile>()
        for (chunk in uids.chunked(10)) {
            val snapshot = profilesCollection
                .whereIn(com.google.firebase.firestore.FieldPath.documentId(), chunk)
                .get()
                .await()
            results.addAll(snapshot.documents.mapNotNull { it.toObject(BuddyProfile::class.java) })
        }
        return results
    }
    
    fun observeBuddyProfiles(uids: List<String>): Flow<List<BuddyProfile>> {
        if (uids.isEmpty()) return kotlinx.coroutines.flow.flowOf(emptyList())
        if (isUsingMock) return mockProfiles.map { map -> uids.mapNotNull { map[it] } }
        
        // Handle >10 buddies by chunking and combining flows
        val chunks = uids.chunked(10)
        val flows = chunks.map { chunk ->
            callbackFlow {
                val registration = profilesCollection
                    .whereIn(com.google.firebase.firestore.FieldPath.documentId(), chunk)
                    .addSnapshotListener { snapshot, _ ->
                        if (snapshot != null) {
                            val profiles = snapshot.documents.mapNotNull { it.toObject(BuddyProfile::class.java) }
                            trySend(profiles)
                        } else {
                            trySend(emptyList())
                        }
                    }
                awaitClose { registration.remove() }
            }
        }

        return if (flows.size == 1) {
            flows.first()
        } else {
            combine(flows) { arrays -> arrays.flatMap { it.toList() } }
        }
    }

    suspend fun sendSOS(myUid: String, needsHelp: Boolean) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            currentMap[myUid] = myProfile.copy(needsHelp = needsHelp)
            mockProfiles.value = currentMap
            return
        }
        profilesCollection.document(myUid).update("needsHelp", needsHelp).await()
    }
}