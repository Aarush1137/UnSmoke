package com.unsmoke.app.core.data.repository

import com.unsmoke.app.core.data.database.dao.CompanionDao
import com.unsmoke.app.core.data.database.entity.CompanionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanionRepository @Inject constructor(
    private val dao: CompanionDao
) {
    fun getCompanion(attemptId: Long): Flow<CompanionEntity?> = dao.getCompanion(attemptId)

    suspend fun insertCompanion(companion: CompanionEntity) {
        dao.insertCompanion(companion)
    }

    suspend fun updateCompanion(companion: CompanionEntity) {
        dao.updateCompanion(companion)
    }
}