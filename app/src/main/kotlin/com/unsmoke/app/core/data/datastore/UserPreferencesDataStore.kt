package com.unsmoke.app.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesDataStore(private val dataStore: DataStore<Preferences>) {
    companion object {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
        val ACTIVE_QUIT_ATTEMPT_ID = longPreferencesKey("active_quit_attempt_id")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val NOTIFICATION_STYLE = stringPreferencesKey("notification_style")
        val THEME = stringPreferencesKey("theme")
        val APP_LOCK_ENABLED = booleanPreferencesKey("app_lock_enabled")
        val CURRENCY = stringPreferencesKey("currency")
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        val LAST_CHECK_IN_DATE = stringPreferencesKey("last_check_in_date")
        val USER_NAME = stringPreferencesKey("user_name")
        val QUIT_REASON = stringPreferencesKey("quit_reason")
        val EMERGENCY_CONTACT_NAME = stringPreferencesKey("emergency_contact_name")
        val EMERGENCY_CONTACT_PHONE = stringPreferencesKey("emergency_contact_phone")
        val PLAN_TRIGGERS = stringSetPreferencesKey("plan_triggers")
        val PLAN_SUPPORTS = stringSetPreferencesKey("plan_supports")
        val PLAN_NRT_PRODUCT = stringPreferencesKey("plan_nrt_product")
        val SHORT_TERM_GOAL = stringPreferencesKey("short_term_goal")
        val LONG_TERM_GOAL = stringPreferencesKey("long_term_goal")
        val BASELINE_BREATH_HOLD = intPreferencesKey("baseline_breath_hold")
        val CURRENT_BREATH_HOLD = intPreferencesKey("current_breath_hold")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
    }

    val onboardingComplete: Flow<Boolean> = dataStore.data.map { it[ONBOARDING_COMPLETE] ?: false }
    val theme: Flow<String> = dataStore.data.map { it[THEME] ?: "DARK" }
    val appLockEnabled: Flow<Boolean> = dataStore.data.map { it[APP_LOCK_ENABLED] ?: false }
    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { it[NOTIFICATIONS_ENABLED] ?: true }
    val notificationStyle: Flow<String> = dataStore.data.map { it[NOTIFICATION_STYLE] ?: "GENTLE" }
    val currencySymbol: Flow<String> = dataStore.data.map { it[CURRENCY_SYMBOL] ?: "₹" }
    val userName: Flow<String> = dataStore.data.map { it[USER_NAME] ?: "Champion" }
    val baselineBreathHold: Flow<Int> = dataStore.data.map { it[BASELINE_BREATH_HOLD] ?: 0 }
    val currentBreathHold: Flow<Int> = dataStore.data.map { it[CURRENT_BREATH_HOLD] ?: 0 }
    val accentColor: Flow<String> = dataStore.data.map { it[ACCENT_COLOR] ?: "MINT" }

    val lastCheckInDate: Flow<String> = dataStore.data.map { it[LAST_CHECK_IN_DATE] ?: "" }

    val emergencyContactName: Flow<String> = dataStore.data.map { it[EMERGENCY_CONTACT_NAME] ?: "" }
    val emergencyContactPhone: Flow<String> = dataStore.data.map { it[EMERGENCY_CONTACT_PHONE] ?: "" }
    val quitReason: Flow<String> = dataStore.data.map { it[QUIT_REASON] ?: "Better health and freedom" }

    suspend fun setOnboardingComplete(complete: Boolean) {
        dataStore.edit { it[ONBOARDING_COMPLETE] = complete }
    }

    suspend fun setLastCheckInDate(dateStr: String) {
        dataStore.edit { it[LAST_CHECK_IN_DATE] = dateStr }
    }

    suspend fun setBreathHold(baseline: Int, current: Int) {
        dataStore.edit {
            it[BASELINE_BREATH_HOLD] = baseline
            it[CURRENT_BREATH_HOLD] = current
        }
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { it[THEME] = theme }
    }

    suspend fun setAppLockEnabled(enabled: Boolean) {
        dataStore.edit { it[APP_LOCK_ENABLED] = enabled }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { it[NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setNotificationStyle(style: String) {
        dataStore.edit { it[NOTIFICATION_STYLE] = style }
    }

    suspend fun setCurrencySymbol(symbol: String) {
        dataStore.edit { it[CURRENCY_SYMBOL] = symbol }
    }

    suspend fun setAccentColor(accent: String) {
        dataStore.edit { it[ACCENT_COLOR] = accent }
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { it[USER_NAME] = name }
    }

    suspend fun setQuitReason(reason: String) {
        dataStore.edit { it[QUIT_REASON] = reason }
    }

    suspend fun setPersonalPlan(triggers: Set<String>, supports: Set<String>, nrtProduct: String) {
        dataStore.edit { prefs ->
            prefs[PLAN_TRIGGERS] = triggers
            prefs[PLAN_SUPPORTS] = supports
            prefs[PLAN_NRT_PRODUCT] = nrtProduct
        }
    }

    suspend fun setGoals(shortTerm: String, longTerm: String) {
        dataStore.edit { prefs ->
            prefs[SHORT_TERM_GOAL] = shortTerm
            prefs[LONG_TERM_GOAL] = longTerm
        }
    }
    suspend fun setEmergencyContact(name: String, phone: String) {
        dataStore.edit {
            it[EMERGENCY_CONTACT_NAME] = name
            it[EMERGENCY_CONTACT_PHONE] = phone
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}