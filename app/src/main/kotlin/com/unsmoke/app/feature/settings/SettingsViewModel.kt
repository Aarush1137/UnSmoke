package com.unsmoke.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.UnSmokeDatabase
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.repository.*
import com.unsmoke.app.core.domain.engine.ExportEngine
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
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
    val currencySymbol: String = "ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¹",
    val accentColor: String = "MINT",
    val version: String = "1.2.0"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: UserPreferencesDataStore,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val database: UnSmokeDatabase
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        combine(dataStore.userName, dataStore.notificationsEnabled, dataStore.notificationStyle, ::Triple),
        combine(dataStore.appLockEnabled, dataStore.theme, dataStore.currencySymbol, ::Triple),
        dataStore.accentColor
    ) { (name, notifEnabled, notifStyle), (lockEnabled, theme, currency), accent ->
        SettingsUiState(
            userName = name,
            notificationsEnabled = notifEnabled,
            notificationStyle = notifStyle,
            appLockEnabled = lockEnabled,
            theme = theme,
            currencySymbol = currency,
            accentColor = accent
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

    fun updateAccentColor(accent: String) {
        viewModelScope.launch {
            dataStore.setAccentColor(accent)
        }
    }

        fun exportData(context: Context) {
        viewModelScope.launch {
            val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull() ?: return@launch
            val uri = ExportEngine.generateExport(context, attempt.id, cravingRepo, nrtRepo)
            if (uri != null) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/csv"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                val chooser = Intent.createChooser(intent, "Export Clinical Data")
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ContextCompat.startActivity(context, chooser, null)
            }
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

