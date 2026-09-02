package com.unsmoke.app.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.unsmoke.app.core.data.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuitAttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attempt: QuitAttemptEntity): Long

    @Insert
    suspend fun insertSmokingEvent(event: SmokingEventEntity): Long

    @Query("SELECT * FROM quit_attempt ORDER BY startEpochMillis DESC")
    fun getAllAttempts(): Flow<List<QuitAttemptEntity>>

    @Query("SELECT * FROM quit_attempt WHERE status = 'ACTIVE' LIMIT 1")
    fun getActiveAttempt(): Flow<QuitAttemptEntity?>
}

@Dao
interface CravingDao {
    @Insert
    suspend fun insert(craving: CravingEventEntity): Long

    @Query("SELECT * FROM craving_event WHERE quitAttemptId = :quitId ORDER BY timestamp DESC")
    fun getCravingsForQuit(quitId: Long): Flow<List<CravingEventEntity>>
}

@Dao
interface NRTDao {
    @Insert
    suspend fun insertUsage(usage: NRTUsageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: NRTProductEntity): Long

    @Query("SELECT * FROM nrt_product")
    fun getAllProducts(): Flow<List<NRTProductEntity>>

    @Query("SELECT * FROM nrt_usage WHERE quitAttemptId = :quitId ORDER BY timestamp DESC")
    fun getUsagesForQuit(quitId: Long): Flow<List<NRTUsageEntity>>

    @Query("DELETE FROM nrt_usage WHERE id = :logId")
    suspend fun deleteUsageById(logId: Long)
}

@Dao
interface CheckInDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkIn: DailyCheckInEntity)

    @Query("SELECT * FROM daily_checkin ORDER BY timestamp DESC")
    fun getAllCheckIns(): Flow<List<DailyCheckInEntity>>
}

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfile(): Flow<UserProfileEntity?>
}

@Dao
interface RewardGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: RewardGoalEntity)

    @Query("SELECT * FROM reward_goal ORDER BY id ASC")
    fun getAllGoals(): Flow<List<RewardGoalEntity>>

    @Query("DELETE FROM reward_goal WHERE id = :goalId")
    suspend fun deleteGoal(goalId: Long)

    @Query("UPDATE reward_goal SET achieved = 1, achievedAt = :timestamp WHERE id = :goalId")
    suspend fun markAchieved(goalId: Long, timestamp: Long)
}