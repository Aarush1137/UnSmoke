package com.unsmoke.app.core.domain.repository

import com.unsmoke.app.core.data.database.entity.*
import kotlinx.coroutines.flow.Flow

interface QuitAttemptRepository {
    suspend fun insertAttempt(attempt: QuitAttemptEntity): Long
    fun getActiveAttempt(): Flow<QuitAttemptEntity?>
    fun getAllAttempts(): Flow<List<QuitAttemptEntity>>
}

interface CravingRepository {
    suspend fun logCraving(craving: CravingEventEntity): Long
    fun getCravings(quitId: Long): Flow<List<CravingEventEntity>>
}

interface NRTRepository {
    suspend fun logUsage(usage: NRTUsageEntity): Long
    suspend fun saveProduct(product: NRTProductEntity): Long
    fun getProducts(): Flow<List<NRTProductEntity>>
    fun getUsage(quitId: Long): Flow<List<NRTUsageEntity>>
}

interface CheckInRepository {
    suspend fun saveCheckIn(checkIn: DailyCheckInEntity)
    fun getAllCheckIns(): Flow<List<DailyCheckInEntity>>
}
