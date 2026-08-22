package com.unsmoke.app.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class ProfileUiState(
    val userName: String = "Champion",
    val quitReason: String = "Better Health & Freedom",
    val smokeFreeSince: String = "Not set yet",
    val daysSmokeFree: Int = 0,
    val cigarettesAvoided: Int = 0,
    val moneySaved: Double = 0.0,
    val cravingsDefeated: Int = 0,
    val currencySymbol: String = "â‚¹",
    val cigsPerDay: Double = 0.0,
    val packPrice: Double = 0.0,
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val hasActiveAttempt: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            combine(
                combine(dataStore.userName, dataStore.quitReason, dataStore.currencySymbol, ::Triple),
                combine(dataStore.emergencyContactName, dataStore.emergencyContactPhone, quitAttemptRepo.getActiveAttempt(), ::Triple)
            ) { (name, reason, currency), (contactName, contactPhone, attempt) ->
                if (attempt != null) {
                    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                    val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))
                    val days = CalculationEngine.smokeFreeDays(attempt.startEpochMillis)
                    val cigsAvoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay).toInt()
                    val saved = CalculationEngine.grossMoneySaved(cigsAvoided.toDouble(), attempt.pricePerCigarette)

                    ProfileUiState(
                        userName = name,
                        quitReason = reason,
                        smokeFreeSince = dateStr,
                        daysSmokeFree = days,
                        cigarettesAvoided = cigsAvoided,
                        moneySaved = saved,
                        currencySymbol = currency,
                        cigsPerDay = attempt.cigarettesPerDay,
                        packPrice = attempt.packPrice,
                        emergencyContactName = contactName,
                        emergencyContactPhone = contactPhone,
                        hasActiveAttempt = true
                    )
                } else {
                    ProfileUiState(
                        userName = name,
                        quitReason = reason,
                        currencySymbol = currency,
                        emergencyContactName = contactName,
                        emergencyContactPhone = contactPhone,
                        hasActiveAttempt = false
                    )
                }
            }.collect { baseState ->
                _uiState.value = baseState
            }
        }
    }

    fun updateEmergencyContact(name: String, phone: String) {
        viewModelScope.launch {
            dataStore.setEmergencyContact(name, phone)
        }
    }

    fun updateQuitReason(reason: String) {
        viewModelScope.launch {
            dataStore.setQuitReason(reason)
        }
    }
}

