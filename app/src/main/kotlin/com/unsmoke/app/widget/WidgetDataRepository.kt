package com.unsmoke.app.widget

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetDataRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository
) {
    companion object {
        val KEY_SMOKE_FREE_DAYS = intPreferencesKey("smoke_free_days")
        val KEY_CIGARETTES_AVOIDED = floatPreferencesKey("cigarettes_avoided")
        val KEY_MONEY_SAVED = floatPreferencesKey("money_saved")
        val KEY_TODAY_CRAVINGS = intPreferencesKey("today_cravings")
        val KEY_TODAY_DEFEATED = intPreferencesKey("today_defeated")
        val KEY_QUIT_DATE_DISPLAY = stringPreferencesKey("quit_date_display")
        val KEY_HAS_ACTIVE_ATTEMPT = booleanPreferencesKey("has_active_attempt")
    }

    suspend fun refreshAllWidgets() {
        val attempt = quitAttemptRepo.getActiveQuitAttempt().first()
        val cravings = cravingRepo.getTodayCravings().first()
        val now = System.currentTimeMillis()

        val days = if (attempt != null) CalculationEngine.smokeFreeDays(attempt.startEpochMillis, now) else 0
        val cigs = if (attempt != null) CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay, now) else 0.0
        val money = if (attempt != null) CalculationEngine.grossMoneySaved(cigs, attempt.pricePerCigarette) else 0.0

        val manager = GlanceAppWidgetManager(context)

        // Update Streak Widget
        manager.getGlanceIds(StreakWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
                prefs.toMutablePreferences().apply {
                    this[KEY_SMOKE_FREE_DAYS] = days
                    this[KEY_QUIT_DATE_DISPLAY] = attempt?.let { formatQuitDate(it.startEpochMillis) } ?: ""
                    this[KEY_HAS_ACTIVE_ATTEMPT] = attempt != null
                }
            }
            StreakWidget().update(context, glanceId)
        }

        // Update Dashboard Widget
        manager.getGlanceIds(DashboardWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
                prefs.toMutablePreferences().apply {
                    this[KEY_SMOKE_FREE_DAYS] = days
                    this[KEY_CIGARETTES_AVOIDED] = cigs.toFloat()
                    this[KEY_MONEY_SAVED] = money.toFloat()
                    this[KEY_TODAY_CRAVINGS] = cravings.size
                    this[KEY_TODAY_DEFEATED] = cravings.count { it.outcome == "DEFEATED" }
                    this[KEY_HAS_ACTIVE_ATTEMPT] = attempt != null
                }
            }
            DashboardWidget().update(context, glanceId)
        }

        // Update Craving Widget
        manager.getGlanceIds(CravingWidget::class.java).forEach { glanceId ->
            CravingWidget().update(context, glanceId)
        }
    }

    private fun formatQuitDate(epochMillis: Long): String {
        val date = java.time.Instant.ofEpochMilli(epochMillis)
            .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        return date.format(java.time.format.DateTimeFormatter.ofPattern("d MMM yyyy"))
    }
}
