package com.unsmoke.app.core.domain.engine

import java.time.Duration

object CalculationEngine {

    /** Total smoke-free duration from quit start to now (or end date). Clamped to 0 if in the future. */
    fun smokeFreeDuration(startEpochMillis: Long, endEpochMillis: Long? = null): Duration {
        val end = endEpochMillis ?: System.currentTimeMillis()
        val diff = maxOf(0L, end - startEpochMillis)
        return Duration.ofMillis(diff)
    }

    /** Cigarettes avoided (fractional e.g., 3.5 days = 3.5 cigs/day). Clamped to 0 if in the future. */
    fun cigarettesAvoided(startEpochMillis: Long, cigarettesPerDay: Double, endEpochMillis: Long? = null): Double {
        val durationMs = maxOf(0L, (endEpochMillis ?: System.currentTimeMillis()) - startEpochMillis)
        val days = durationMs / (1000.0 * 60 * 60 * 24)
        return days * cigarettesPerDay
    }

    /** Gross money saved from not buying cigarettes */
    fun grossMoneySaved(cigarettesAvoided: Double, pricePerCigarette: Double): Double {
        return cigarettesAvoided * pricePerCigarette
    }

    /** Total NRT expenditure from logged NRT usage */
    fun nrtExpenditure(nrtUsageList: List<Pair<Int, Double>>): Double {
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

    /** Countdown days to a future quit date. Returns 0 if already started. */
    fun daysUntilQuit(startEpochMillis: Long): Int {
        val now = System.currentTimeMillis()
        if (now >= startEpochMillis) return 0
        val diffMs = startEpochMillis - now
        return (diffMs / (1000.0 * 60 * 60 * 24)).toInt()
    }

    /** Returns true if the quit date is in the future */
    fun isPlannedQuit(startEpochMillis: Long): Boolean {
        return System.currentTimeMillis() < startEpochMillis
    }

    /** Format duration for display */
    fun formatDuration(startEpochMillis: Long): String {
        val duration = smokeFreeDuration(startEpochMillis)
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        return when {
            days >= 1 -> "d h"
            hours >= 1 -> "h m"
            else -> "m"
        }
    }
}
