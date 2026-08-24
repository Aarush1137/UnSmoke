package com.unsmoke.app.core.data.repository

import com.unsmoke.app.core.data.database.dao.TitrationLogDao
import com.unsmoke.app.core.data.database.entity.TitrationLogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TitrationRepository @Inject constructor(
    private val dao: TitrationLogDao
) {
    fun getLogsForAttempt(attemptId: Long): Flow<List<TitrationLogEntity>> = dao.getLogsForAttempt(attemptId)

    suspend fun insertLog(log: TitrationLogEntity) {
        dao.insertLog(log)
    }

    suspend fun deleteLog(logId: Long) {
        dao.deleteLog(logId)
    }
}