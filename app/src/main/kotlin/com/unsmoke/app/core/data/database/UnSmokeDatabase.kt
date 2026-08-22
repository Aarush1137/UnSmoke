package com.unsmoke.app.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unsmoke.app.core.data.database.dao.*
import com.unsmoke.app.core.data.database.entity.*

@Database(
    entities = [
        UserProfileEntity::class,
        QuitAttemptEntity::class,
        SmokingBaselineEntity::class,
        SmokingEventEntity::class,
        CravingEventEntity::class,
        NRTProductEntity::class,
        NRTUsageEntity::class,
        NRTReminderEntity::class,
        MoodEntryEntity::class,
        DailyCheckInEntity::class,
        JournalEntryEntity::class,
        AchievementEntity::class,
        QuitReasonEntity::class,
        RewardGoalEntity::class,
        NotificationPreferenceEntity::class,
        ImplementationIntentionEntity::class,
        TriggerLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class UnSmokeDatabase : RoomDatabase() {
    abstract fun quitAttemptDao(): QuitAttemptDao
    abstract fun cravingDao(): CravingDao
    abstract fun nrtDao(): NRTDao
    abstract fun checkInDao(): CheckInDao
    abstract fun userProfileDao(): UserProfileDao
}

