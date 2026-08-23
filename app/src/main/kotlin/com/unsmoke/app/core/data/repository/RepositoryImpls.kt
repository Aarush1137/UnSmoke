package com.unsmoke.app.core.data.repository

import com.unsmoke.app.core.data.database.dao.*
import com.unsmoke.app.core.data.database.entity.*
import com.unsmoke.app.core.domain.repository.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuitAttemptRepositoryImpl @Inject constructor(
    private val dao: QuitAttemptDao
) : QuitAttemptRepository {
    override suspend fun insertAttempt(attempt: QuitAttemptEntity) = dao.insert(attempt)
    override fun getActiveAttempt(): Flow<QuitAttemptEntity?> = dao.getActiveAttempt()
    override fun getAllAttempts(): Flow<List<QuitAttemptEntity>> = dao.getAllAttempts()
}

@Singleton
class CravingRepositoryImpl @Inject constructor(
    private val dao: CravingDao
) : CravingRepository {
    override suspend fun logCraving(craving: CravingEventEntity) = dao.insert(craving)
    override fun getCravings(quitId: Long): Flow<List<CravingEventEntity>> = dao.getCravingsForQuit(quitId)
}

@Singleton
class NRTRepositoryImpl @Inject constructor(
    private val dao: NRTDao
) : NRTRepository {
    override suspend fun logUsage(usage: NRTUsageEntity) = dao.insertUsage(usage)
    override suspend fun saveProduct(product: NRTProductEntity) = dao.insertProduct(product)
    override fun getProducts(): Flow<List<NRTProductEntity>> = dao.getAllProducts()
    override fun getUsage(quitId: Long): Flow<List<NRTUsageEntity>> = dao.getUsagesForQuit(quitId)
    override suspend fun deleteUsageById(logId: Long) = dao.deleteUsageById(logId)
}

@Singleton
class CheckInRepositoryImpl @Inject constructor(
    private val dao: CheckInDao
) : CheckInRepository {
    override suspend fun saveCheckIn(checkIn: DailyCheckInEntity) = dao.insert(checkIn)
    override fun getAllCheckIns(): Flow<List<DailyCheckInEntity>> = dao.getAllCheckIns()
}
