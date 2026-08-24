package com.unsmoke.app.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 3,
    exportSchema = true
)
abstract class UnSmokeDatabase : RoomDatabase() {
    companion object {
                val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE quit_attempt ADD COLUMN substanceType TEXT NOT NULL DEFAULT 'CIGARETTE'")
                db.execSQL("ALTER TABLE quit_attempt ADD COLUMN nicotineStrengthMg REAL DEFAULT NULL")
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE craving_event ADD COLUMN latitude REAL DEFAULT NULL")
                db.execSQL("ALTER TABLE craving_event ADD COLUMN longitude REAL DEFAULT NULL")
            }
        }
    }
    abstract fun quitAttemptDao(): QuitAttemptDao
    abstract fun cravingDao(): CravingDao
    abstract fun nrtDao(): NRTDao
    abstract fun checkInDao(): CheckInDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun rewardGoalDao(): RewardGoalDao
}

