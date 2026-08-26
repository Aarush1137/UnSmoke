package com.unsmoke.app.core.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

data class BuddyProfile(
    val uid: String = "",
    val pairingCode: String = "",
    val buddyUid: String? = null,
    val needsHelp: Boolean = false,
    val pendingBuddyRequestUid: String? = null
)

@Singleton
class BuddyRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val profilesCollection = firestore.collection("profiles")

    // Mock Backend for when Firebase API fails (e.g., Missing Firestore Database, missing permissions, API key issues)
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
            
            // Ensure profile exists with a 6-digit code
            val doc = profilesCollection.document(uid).get().await()
            if (!doc.exists()) {
                val code = (100000..999999).random().toString()
                profilesCollection.document(uid).set(BuddyProfile(uid = uid, pairingCode = code)).await()
            }
            isUsingMock = false
            uid
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to Mock Backend
            isUsingMock = true
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
            currentMap[targetEntry.key] = targetEntry.value.copy(pendingBuddyRequestUid = myUid)
            mockProfiles.value = currentMap
            return true
        }

        val snapshot = profilesCollection.whereEqualTo("pairingCode", buddyCode).get().await()
        if (snapshot.isEmpty) return false
        
        val targetUid = snapshot.documents.first().id
        profilesCollection.document(targetUid).update("pendingBuddyRequestUid", myUid).await()
        return true
    }

    suspend fun acceptBuddyRequest(myUid: String, requesterUid: String) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            val requesterProfile = currentMap[requesterUid]
            
            currentMap[myUid] = myProfile.copy(buddyUid = requesterUid, pendingBuddyRequestUid = null)
            if (requesterProfile != null) {
                currentMap[requesterUid] = requesterProfile.copy(buddyUid = myUid)
            }
            mockProfiles.value = currentMap
            return
        }

        profilesCollection.document(myUid).update(
            mapOf("buddyUid" to requesterUid, "pendingBuddyRequestUid" to null)
        ).await()
        profilesCollection.document(requesterUid).update("buddyUid", myUid).await()
    }

    suspend fun rejectBuddyRequest(myUid: String) {
        if (isUsingMock) {
            val currentMap = mockProfiles.value.toMutableMap()
            val myProfile = currentMap[myUid] ?: return
            currentMap[myUid] = myProfile.copy(pendingBuddyRequestUid = null)
            mockProfiles.value = currentMap
            return
        }

        profilesCollection.document(myUid).update("pendingBuddyRequestUid", null).await()
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
    
    fun observeBuddyProfile(buddyUid: String): Flow<BuddyProfile?> {
        if (isUsingMock) return mockProfiles.map { it[buddyUid] }
        return callbackFlow {
            val registration = profilesCollection.document(buddyUid)
                .addSnapshotListener { snapshot, _ ->
                    val profile = snapshot?.toObject(BuddyProfile::class.java)
                    trySend(profile)
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