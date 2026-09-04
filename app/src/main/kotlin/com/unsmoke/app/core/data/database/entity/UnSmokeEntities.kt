package com.unsmoke.app.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import androidx.room.ForeignKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String?,
    val notificationStyle: String,
    val currency: String = "INR",
    val currencySymbol: String = "\u20B9",
    val appLockEnabled: Boolean = false,
    val theme: String = "SYSTEM",
    val createdAt: Long
)

@Entity(tableName = "quit_attempt")
data class QuitAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startEpochMillis: Long,
    val endEpochMillis: Long?,
    val status: String,
    val cigarettesPerDay: Double,
    val cigarettesPerPack: Int,
    val packPrice: Double,
    val substanceType: String = "CIGARETTE",
    val nicotineStrengthMg: Double? = null,
    val pricePerCigarette: Double,
    val timezone: String,
    val createdAt: Long
)

@Entity(tableName = "smoking_baseline")
data class SmokingBaselineEntity(
    @PrimaryKey val quitAttemptId: Long,
    val yearsSmoked: Double?,
    val weekendCigsPerDay: Double?,
    val firstCigAfterWakingMinutes: Int?,
    val strongestPeriod: String?
)

@Entity(tableName = "smoking_event")
data class SmokingEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quitAttemptId: Long,
    val timestamp: Long,
    val cigaretteCount: Int,
    val trigger: String?,
    val mood: Int?,
    val notes: String?,
    val eventType: String
)

@Entity(tableName = "craving_event")
data class CravingEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quitAttemptId: Long,
    val timestamp: Long,
    val intensity: Int,
    val trigger: String?,
    val location: String?,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val intervention: String?,
    val outcome: String,
    val durationSeconds: Long?,
    val nrtUsedBefore: Boolean,
    val mood: Int?
)

@Entity(tableName = "nrt_product")
data class NRTProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val name: String,
    val nicotineStrengthMg: Double?,
    val packPrice: Double,
    val unitsPerPack: Int,
    val pricePerUnit: Double,
    val isActive: Boolean = true
)

@Entity(tableName = "nrt_usage")
data class NRTUsageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val quitAttemptId: Long,
    val timestamp: Long,
    val quantity: Int,
    val cravingBefore: Int?,
    val cravingAfter: Int?,
    val trigger: String?,
    val notes: String?
)

@Entity(tableName = "nrt_reminder")
data class NRTReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true,
    val label: String?
)

@Entity(tableName = "mood_entry")
data class MoodEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val datestamp: String,
    val mood: Int,
    val emotions: String,
    val notes: String?
)

@Entity(tableName = "daily_checkin", indices = [Index(value = ["datestamp"], unique = true)])
data class DailyCheckInEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val datestamp: String,
    val dayRating: String,
    val smoked: Boolean,
    val cravingLevel: Int,
    val topHelper: String?,
    val tomorrowFocus: String?,
    val timestamp: Long
)

@Entity(tableName = "journal_entry")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val content: String,
    val mood: Int?,
    val tags: String
)

@Entity(tableName = "achievement")
data class AchievementEntity(
    @PrimaryKey val type: String,
    val unlockedAt: Long?,
    val displayedToUser: Boolean = false
)

@Entity(tableName = "quit_reason")
data class QuitReasonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quitAttemptId: Long,
    val category: String,
    val customText: String?
)

@Entity(tableName = "reward_goal")
data class RewardGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val achieved: Boolean = false,
    val achievedAt: Long?
)

@Entity(tableName = "notification_preference")
data class NotificationPreferenceEntity(
    @PrimaryKey val type: String,
    val enabled: Boolean,
    val scheduledHour: Int?,
    val scheduledMinute: Int?
)

@Entity(tableName = "implementation_intention")
data class ImplementationIntentionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trigger: String,
    val action: String,
    val quitAttemptId: Long
)

@Entity(tableName = "trigger_log")
data class TriggerLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trigger: String,
    val count: Int,
    val lastSeen: Long,
    val hourOfDay: Int,
    val quitAttemptId: Long
)


@Entity(
    tableName = "titration_log",
    foreignKeys = [
        ForeignKey(
            entity = QuitAttemptEntity::class,
            parentColumns = ["id"],
            childColumns = ["quitAttemptId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["quitAttemptId"])]
)
data class TitrationLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quitAttemptId: Long,
    val nicotineStrengthMg: Double,
    val timestamp: Long,
    val notes: String?
)
@Entity(
    tableName = "virtual_companion",
    foreignKeys = [
        ForeignKey(
            entity = QuitAttemptEntity::class,
            parentColumns = ["id"],
            childColumns = ["quitAttemptId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["quitAttemptId"])]
)
data class CompanionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quitAttemptId: Long,
    val name: String,
    val health: Int,
    val stage: Int,
    val lastInteractionTime: Long,
    val mood: String
)