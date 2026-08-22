package com.unsmoke.app.core.domain.engine

import java.time.Duration

object CalculationEngine {

    /** Total smoke-free duration from quit start to now (or end date) */
    fun smokeFreeDuration(startEpochMillis: Long, endEpochMillis: Long? = null): Duration {
        val end = endEpochMillis ?: System.currentTimeMillis()
        return Duration.ofMillis(end - startEpochMillis)
    }

    /** Cigarettes avoided (fractional — e.g., 3.5 days = 3.5 × cigs/day) */
    fun cigarettesAvoided(startEpochMillis: Long, cigarettesPerDay: Double, endEpochMillis: Long? = null): Double {
        val durationMs = (endEpochMillis ?: System.currentTimeMillis()) - startEpochMillis
        val days = durationMs / (1000.0 * 60 * 60 * 24)
        return days * cigarettesPerDay
    }

    /** Gross money saved from not buying cigarettes */
    fun grossMoneySaved(cigarettesAvoided: Double, pricePerCigarette: Double): Double {
        return cigarettesAvoided * pricePerCigarette
    }

    /** Total NRT expenditure from logged NRT usage */
    fun nrtExpenditure(nrtUsageList: List<Pair<Int, Double>>): Double {
        // Pair<quantity, pricePerUnit>
        return nrtUsageList.sumOf { (qty, price) -> qty * price }
    }

    /** Net savings = gross - NRT expenditure */
    fun netMoneySaved(grossSaved: Double, nrtExpenditure: Double): Double {
        return grossSaved - nrtExpenditure
    }

    /** Whole cigarette packs avoided */
    fun packsAvoided(cigarettesAvoided: Double, cigarettesPerPack: Int): Double {
        return cigarettesAvoided / cigarettesPerPack
    }

    /** Smoke-free days (whole days) */
    fun smokeFreeDays(startEpochMillis: Long, endEpochMillis: Long? = null): Int {
        val duration = smokeFreeDuration(startEpochMillis, endEpochMillis)
        return duration.toDays().toInt()
    }

    /** Current streak in days (0 if relapsed) */
    fun currentStreak(activeAttemptStartMillis: Long?, lastSmokeEventMillis: Long? = null): Int {
        if (activeAttemptStartMillis == null) return 0
        val streakStart = maxOf(activeAttemptStartMillis, lastSmokeEventMillis?.plus(1) ?: activeAttemptStartMillis)
        return smokeFreeDays(streakStart)
    }

    /** Longest streak across all attempts */
    fun longestStreak(attempts: List<Pair<Long, Long?>>): Int {
        // Pair<startMillis, endMillis?>
        return attempts.maxOfOrNull { (start, end) -> smokeFreeDays(start, end) } ?: 0
    }

    /** Total smoke-free days across all attempts */
    fun totalSmokeFreeDays(attempts: List<Pair<Long, Long?>>): Int {
        return attempts.sumOf { (start, end) -> smokeFreeDays(start, end) }
    }

    /** Format duration for display */
    fun formatDuration(startEpochMillis: Long): String {
        val duration = smokeFreeDuration(startEpochMillis)
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        return when {
            days >= 1 -> "${days}d ${hours}h"
            hours >= 1 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }

    /** NRT cost per unit from pack info */
    fun nrtCostPerUnit(packPrice: Double, unitsPerPack: Int): Double {
        return if (unitsPerPack > 0) packPrice / unitsPerPack else 0.0
    }
}
