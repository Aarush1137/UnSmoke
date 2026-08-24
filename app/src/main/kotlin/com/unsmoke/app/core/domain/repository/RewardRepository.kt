package com.unsmoke.app.core.domain.repository

import com.unsmoke.app.core.data.database.dao.RewardGoalDao
import com.unsmoke.app.core.data.database.entity.RewardGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardRepository @Inject constructor(
    private val rewardGoalDao: RewardGoalDao
) {
    fun getAllGoals(): Flow<List<RewardGoalEntity>> = rewardGoalDao.getAllGoals()

    suspend fun insertGoal(goal: RewardGoalEntity) {
        rewardGoalDao.insert(goal)
    }

    suspend fun deleteGoal(goalId: Long) {
        rewardGoalDao.deleteGoal(goalId)
    }

    suspend fun markGoalAchieved(goalId: Long) {
        rewardGoalDao.markAchieved(goalId, System.currentTimeMillis())
    }
}