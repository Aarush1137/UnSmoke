package com.unsmoke.app.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unsmoke.app.core.data.database.entity.TitrationLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TitrationLogDao {

    @Query("SELECT * FROM titration_log WHERE quitAttemptId = :attemptId ORDER BY timestamp ASC")
    fun getLogsForAttempt(attemptId: Long): Flow<List<TitrationLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: TitrationLogEntity)

    @Query("DELETE FROM titration_log WHERE id = :logId")
    suspend fun deleteLog(logId: Long)
}