package com.unsmoke.app.core.domain.engine

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudBackupEngine @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository
) {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun syncLocalDataToCloud() {
        val uid = auth.currentUser?.uid ?: return
        
        try {
            val userRef = firestore.collection("profiles").document(uid)
            val backupRef = firestore.collection("backups").document(uid)

            // 1. Refresh TTL: 30 days from now
            val expireAt = Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)
            
            userRef.set(mapOf("expireAt" to expireAt), SetOptions.merge()).await()

            // 2. Backup Local Data
            val attempts = quitAttemptRepo.getAllAttempts().firstOrNull() ?: emptyList()
            val activeAttempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
            val cravings = activeAttempt?.let { cravingRepo.getCravings(it.id).firstOrNull() } ?: emptyList()
            val nrtUsage = activeAttempt?.let { nrtRepo.getUsage(it.id).firstOrNull() } ?: emptyList()

            val backupData = mapOf(
                "expireAt" to expireAt,
                "lastBackupTimestamp" to System.currentTimeMillis(),
                "quitAttempts" to attempts,
                "cravings" to cravings,
                "nrtUsage" to nrtUsage
            )
            
            backupRef.set(backupData, SetOptions.merge()).await()
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}