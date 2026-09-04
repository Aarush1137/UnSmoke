package com.unsmoke.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.UnSmokeDatabase
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.repository.*
import com.unsmoke.app.core.domain.engine.ExportEngine
import com.unsmoke.app.core.domain.engine.CloudBackupEngine
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
    val currencySymbol: String = "\u20B9",
    val accentColor: String = "MINT",
    val version: String = com.unsmoke.app.BuildConfig.VERSION_NAME,
    val isBackingUp: Boolean = false,
    val backupMessage: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: UserPreferencesDataStore,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val database: UnSmokeDatabase,
    private val cloudBackupEngine: CloudBackupEngine
) : ViewModel() {

    private val _backupState = MutableStateFlow(false)
    private val _backupMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SettingsUiState> = combine(
        combine(dataStore.userName, dataStore.notificationsEnabled, dataStore.notificationStyle, ::Triple),
        combine(dataStore.appLockEnabled, dataStore.theme, dataStore.currencySymbol, ::Triple),
        dataStore.accentColor,
        _backupState,
        _backupMessage
    ) { (name, notifEnabled, notifStyle), (lockEnabled, theme, currency), accent, isBackingUp, backupMsg ->
        SettingsUiState(
            userName = name,
            notificationsEnabled = notifEnabled,
            notificationStyle = notifStyle,
            appLockEnabled = lockEnabled,
            theme = theme,
            currencySymbol = currency,
            accentColor = accent,
            isBackingUp = isBackingUp,
            backupMessage = backupMsg
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

    suspend fun generateExportUri(context: Context): android.net.Uri? {
        val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull() ?: return null
        return ExportEngine.generateExport(context, attempt.id, cravingRepo, nrtRepo)
    }

    fun syncToCloud() {
        viewModelScope.launch {
            _backupState.value = true
            try {
                cloudBackupEngine.syncLocalDataToCloud()
                _backupMessage.value = "Backup complete!"
            } catch (e: Exception) {
                _backupMessage.value = "Backup failed: ${e.message}"
            } finally {
                _backupState.value = false
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
