package com.unsmoke.app.core.domain.engine

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val type: BadgeType,
    val tier: BadgeTier,
    val conditionToUnlock: (smokeFreeDays: Int, cravingsDefeated: Int) -> Boolean
)

enum class BadgeType {
    MILESTONE, CRAVING, CONSISTENCY, SPECIAL
}

enum class BadgeTier {
    BRONZE, SILVER, GOLD, PLATINUM
}

enum class BadgeState {
    EARNED, LOCKED, SECRET
}

object AchievementEngine {

    val allBadges = listOf(
        // Milestones
        AchievementBadge("m_1", "First Step", "24 hours smoke-free.", BadgeType.MILESTONE, BadgeTier.BRONZE) { days, _ -> days >= 1 },
        AchievementBadge("m_3", "Three Days", "The physical withdrawal peak is ending.", BadgeType.MILESTONE, BadgeTier.BRONZE) { days, _ -> days >= 3 },
        AchievementBadge("m_7", "One Week", "7 days of breathing easier.", BadgeType.MILESTONE, BadgeTier.SILVER) { days, _ -> days >= 7 },
        AchievementBadge("m_14", "Two Weeks", "14 days of freedom.", BadgeType.MILESTONE, BadgeTier.SILVER) { days, _ -> days >= 14 },
        AchievementBadge("m_21", "Three Weeks", "21 days. The hardest part is over.", BadgeType.MILESTONE, BadgeTier.SILVER) { days, _ -> days >= 21 },
        AchievementBadge("m_30", "One Month", "30 solid days. A huge milestone.", BadgeType.MILESTONE, BadgeTier.GOLD) { days, _ -> days >= 30 },
        AchievementBadge("m_60", "Two Months", "60 days of taking your life back.", BadgeType.MILESTONE, BadgeTier.GOLD) { days, _ -> days >= 60 },
        AchievementBadge("m_90", "Three Months", "90 days. Your brain dopamine is resetting.", BadgeType.MILESTONE, BadgeTier.GOLD) { days, _ -> days >= 90 },
        AchievementBadge("m_180", "Half a Year", "180 days. You are a new person.", BadgeType.MILESTONE, BadgeTier.PLATINUM) { days, _ -> days >= 180 },
        AchievementBadge("m_365", "One Year", "365 days of strength. A full orbit.", BadgeType.MILESTONE, BadgeTier.PLATINUM) { days, _ -> days >= 365 },
        
        // Cravings
        AchievementBadge("c_1", "Craving Crusher", "Defeat your first craving.", BadgeType.CRAVING, BadgeTier.BRONZE) { _, cravings -> cravings >= 1 },
        AchievementBadge("c_5", "Mind over Matter", "Defeat 5 cravings.", BadgeType.CRAVING, BadgeTier.BRONZE) { _, cravings -> cravings >= 5 },
        AchievementBadge("c_10", "Iron Will", "Defeat 10 cravings.", BadgeType.CRAVING, BadgeTier.SILVER) { _, cravings -> cravings >= 10 },
        AchievementBadge("c_25", "Steel Focus", "Defeat 25 cravings.", BadgeType.CRAVING, BadgeTier.SILVER) { _, cravings -> cravings >= 25 },
        AchievementBadge("c_50", "Unshakeable", "Defeat 50 cravings. Nothing can break you.", BadgeType.CRAVING, BadgeTier.GOLD) { _, cravings -> cravings >= 50 },
        AchievementBadge("c_100", "Zen Master", "Defeat 100 cravings. Total mastery.", BadgeType.CRAVING, BadgeTier.PLATINUM) { _, cravings -> cravings >= 100 },
        
        // Special/Consistency
        AchievementBadge("s_1", "The First Step", "Started the journey.", BadgeType.SPECIAL, BadgeTier.BRONZE) { _, _ -> true }
    )

    fun getBadgeState(badge: AchievementBadge, smokeFreeDays: Int, cravingsDefeated: Int): BadgeState {
        val unlocked = badge.conditionToUnlock(smokeFreeDays, cravingsDefeated)
        return if (unlocked) {
            BadgeState.EARNED
        } else {
            // Secret badges logic could go here, for now they are locked until earned
            if (badge.tier == BadgeTier.PLATINUM && !unlocked) BadgeState.SECRET else BadgeState.LOCKED
        }
    }

}
