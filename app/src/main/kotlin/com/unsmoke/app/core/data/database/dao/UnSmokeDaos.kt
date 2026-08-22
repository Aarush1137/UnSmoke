package com.unsmoke.app.core.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.unsmoke.app.core.data.database.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuitAttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attempt: QuitAttemptEntity): Long

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
