package com.unsmoke.app.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsmoke.app.core.data.database.entity.CompanionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionDao {

    @Query("SELECT * FROM virtual_companion WHERE quitAttemptId = :attemptId LIMIT 1")
    fun getCompanion(attemptId: Long): Flow<CompanionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanion(companion: CompanionEntity)

    @Update
    suspend fun updateCompanion(companion: CompanionEntity)
}