package com.unsmoke.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.UnSmokeDatabase
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class SettingsUiState(
    val userName: String = "Champion",
    val notificationsEnabled: Boolean = true,
    val notificationStyle: String = "GENTLE",
    val appLockEnabled: Boolean = false,
    val theme: String = "DARK",
    val currencySymbol: String = "?",
    val version: String = "1.0.0-alpha"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: UserPreferencesDataStore,
    private val database: UnSmokeDatabase
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        combine(dataStore.userName, dataStore.notificationsEnabled, dataStore.notificationStyle, ::Triple),
        combine(dataStore.appLockEnabled, dataStore.theme, dataStore.currencySymbol, ::Triple)
    ) { (name, notifEnabled, notifStyle), (lockEnabled, theme, currency) ->
        SettingsUiState(
            userName = name,
            notificationsEnabled = notifEnabled,
            notificationStyle = notifStyle,
            appLockEnabled = lockEnabled,
            theme = theme,
            currencySymbol = currency
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    fun updateName(name: String) {
        viewModelScope.launch {
            dataStore.setUserName(name)
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setNotificationsEnabled(enabled)
        }
    }

    fun updateNotificationStyle(style: String) {
        viewModelScope.launch {
            dataStore.setNotificationStyle(style)
        }
    }

    fun toggleAppLock(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setAppLockEnabled(enabled)
        }
    }

    fun updateTheme(theme: String) {
        viewModelScope.launch {
            dataStore.setTheme(theme)
        }
    }

    fun updateCurrencySymbol(symbol: String) {
        viewModelScope.launch {
            dataStore.setCurrencySymbol(symbol)
        }
    }

    fun wipeAllData(onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.clearAllTables()
            }
            dataStore.clearAll()
            onComplete()
        }
    }
}

