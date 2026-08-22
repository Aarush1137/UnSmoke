package com.unsmoke.app.core.domain.engine

import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import java.time.Instant
import java.time.ZoneId

object PersonalizationEngine {

    /**
     * Identifies the 2-hour window where the user logs the most cravings.
     * Needs at least 5 cravings to make a suggestion.
     * Returns a string like "7 PM - 9 PM" or null if insufficient data.
     */
    fun getHighRiskTimeWindow(cravings: List<CravingEventEntity>): String? {
        if (cravings.size < 5) return null

        val hourCounts = mutableMapOf<Int, Int>()
        cravings.forEach { craving ->
            val hour = Instant.ofEpochMilli(craving.timestamp)
                .atZone(ZoneId.systemDefault())
                .hour
            hourCounts[hour] = hourCounts.getOrDefault(hour, 0) + 1
        }

        // Find the peak hour
        val peakHour = hourCounts.maxByOrNull { it.value }?.key ?: return null
        val nextHour = (peakHour + 2) % 24

        fun formatHour(h: Int): String {
            val amPm = if (h < 12) "AM" else "PM"
            val displayHour = if (h % 12 == 0) 12 else h % 12
            return " "
        }

        return " - "
    }

    /**
     * Ranks the user's previously used coping strategies based on how often they resulted in 'DEFEATED'.
     * Returns a ranked list of intervention names.
     */
    fun getRankedCopingStrategies(cravings: List<CravingEventEntity>): List<String> {
        val interventionStats = mutableMapOf<String, Pair<Int, Int>>() // Name -> Pair(Total Uses, Successes)
        
        cravings.forEach {
            val intervention = it.intervention ?: return@forEach
            val stats = interventionStats.getOrDefault(intervention, Pair(0, 0))
            val isSuccess = if (it.outcome == "DEFEATED") 1 else 0
            interventionStats[intervention] = Pair(stats.first + 1, stats.second + isSuccess)
        }

        return interventionStats.entries
            .filter { it.value.first > 0 }
            .sortedByDescending { it.value.second.toDouble() / it.value.first } // Sort by success rate
            .map { it.key }
    }

    /**
     * Returns a suggested intervention based on current intensity and trigger.
     */
    fun suggestIntervention(intensity: Int, trigger: String, rankedStrategies: List<String>): String {
        return when {
            intensity >= 8 -> "10-Minute Reset"
            trigger.contains("Stress", ignoreCase = true) -> "Breathing Exercise"
            trigger.contains("Boredom", ignoreCase = true) -> "Distraction"
            trigger.contains("Social", ignoreCase = true) -> "Exit Environment"
            rankedStrategies.isNotEmpty() -> rankedStrategies.first()
            else -> "Drink Water"
        }
    }
}
