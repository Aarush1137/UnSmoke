package com.unsmoke.app.core.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

data class BuddyProfile(
    val uid: String = "",
    val pairingCode: String = "",
    val buddyUid: String? = null,
    val needsHelp: Boolean = false
)

@Singleton
class BuddyRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val profilesCollection = firestore.collection("profiles")

    suspend fun signInAnonymously(): String {
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
        return uid
    }

    suspend fun pairWithCode(myUid: String, buddyCode: String): Boolean {
        // Find user with that code
        val snapshot = profilesCollection.whereEqualTo("pairingCode", buddyCode).get().await()
        if (snapshot.isEmpty) return false
        
        val buddyUid = snapshot.documents.first().id
        
        // Update both profiles
        profilesCollection.document(myUid).update("buddyUid", buddyUid).await()
        profilesCollection.document(buddyUid).update("buddyUid", myUid).await()
        return true
    }

    fun observeMyProfile(myUid: String): Flow<BuddyProfile?> = callbackFlow {
        val registration = profilesCollection.document(myUid)
            .addSnapshotListener { snapshot, _ ->
                val profile = snapshot?.toObject(BuddyProfile::class.java)
                trySend(profile)
            }
        awaitClose { registration.remove() }
    }
    
    fun observeBuddyProfile(buddyUid: String): Flow<BuddyProfile?> = callbackFlow {
        val registration = profilesCollection.document(buddyUid)
            .addSnapshotListener { snapshot, _ ->
                val profile = snapshot?.toObject(BuddyProfile::class.java)
                trySend(profile)
            }
        awaitClose { registration.remove() }
    }

    suspend fun sendSOS(myUid: String, needsHelp: Boolean) {
        profilesCollection.document(myUid).update("needsHelp", needsHelp).await()
    }
}