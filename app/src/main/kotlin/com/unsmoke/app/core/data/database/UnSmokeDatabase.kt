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
        TriggerLogEntity::class,
        TitrationLogEntity::class,
        CompanionEntity::class
    ],
    version = 6,
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

                val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `titration_log` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quitAttemptId` INTEGER NOT NULL, `nicotineStrengthMg` REAL NOT NULL, `timestamp` INTEGER NOT NULL, `notes` TEXT, FOREIGN KEY(`quitAttemptId`) REFERENCES `quit_attempt`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )"
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_titration_log_quitAttemptId` ON `titration_log` (`quitAttemptId`)")
            }
        }

                val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `virtual_companion` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quitAttemptId` INTEGER NOT NULL, `name` TEXT NOT NULL, `health` INTEGER NOT NULL, `stage` INTEGER NOT NULL, `lastInteractionTime` INTEGER NOT NULL, `mood` TEXT NOT NULL, FOREIGN KEY(`quitAttemptId`) REFERENCES `quit_attempt`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )"
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_virtual_companion_quitAttemptId` ON `virtual_companion` (`quitAttemptId`)")
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE craving_event ADD COLUMN latitude REAL DEFAULT NULL")
                db.execSQL("ALTER TABLE craving_event ADD COLUMN longitude REAL DEFAULT NULL")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_daily_checkin_datestamp` ON `daily_checkin` (`datestamp`)")
            }
        }
    }
    abstract fun companionDao(): CompanionDao
    abstract fun titrationLogDao(): TitrationLogDao
    abstract fun quitAttemptDao(): QuitAttemptDao
    abstract fun cravingDao(): CravingDao
    abstract fun nrtDao(): NRTDao
    abstract fun checkInDao(): CheckInDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun rewardGoalDao(): RewardGoalDao
}

