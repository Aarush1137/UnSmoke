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
        val NOTIFICATION_STYLE = stringPreferencesKey("notification_style")
        val THEME = stringPreferencesKey("theme")
        val APP_LOCK_ENABLED = booleanPreferencesKey("app_lock_enabled")
        val CURRENCY = stringPreferencesKey("currency")
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        val LAST_CHECK_IN_DATE = stringPreferencesKey("last_check_in_date")
    }

    val onboardingComplete: Flow<Boolean> = dataStore.data.map { it[ONBOARDING_COMPLETE] ?: false }
    val theme: Flow<String> = dataStore.data.map { it[THEME] ?: "SYSTEM" }

    suspend fun setOnboardingComplete(complete: Boolean) {
        dataStore.edit { it[ONBOARDING_COMPLETE] = complete }
    }
}
