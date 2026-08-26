package com.unsmoke.app.core.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
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
class BuddyRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
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
            if (!doc.exists()) {
                val code = (100000..999999).random().toString()
                profilesCollection.document(uid).set(BuddyProfile(uid = uid, pairingCode = code)).await()
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

        val snapshot = profilesCollection.whereEqualTo("pairingCode", buddyCode).get().await()
        if (snapshot.isEmpty) return false
        
        val targetUid = snapshot.documents.first().id
        profilesCollection.document(targetUid).update("pendingBuddyRequestUids", FieldValue.arrayUnion(myUid)).await()
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

        profilesCollection.document(myUid).update(
            "buddyUids", FieldValue.arrayUnion(requesterUid),
            "pendingBuddyRequestUids", FieldValue.arrayRemove(requesterUid)
        ).await()
        profilesCollection.document(requesterUid).update("buddyUids", FieldValue.arrayUnion(myUid)).await()
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

        profilesCollection.document(myUid).update("pendingBuddyRequestUids", FieldValue.arrayRemove(requesterUid)).await()
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
    
    fun observeBuddyProfiles(uids: List<String>): Flow<List<BuddyProfile>> {
        if (uids.isEmpty()) return kotlinx.coroutines.flow.flowOf(emptyList())
        if (isUsingMock) return mockProfiles.map { map -> uids.mapNotNull { map[it] } }
        
        return callbackFlow {
            // Watch all docs where FieldPath.documentId() in uids
            // Note: Firestore 'in' queries are limited to 10 items
            if (uids.size > 10) {
                trySend(emptyList())
                return@callbackFlow
            }
            val registration = profilesCollection.whereIn(com.google.firebase.firestore.FieldPath.documentId(), uids)
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