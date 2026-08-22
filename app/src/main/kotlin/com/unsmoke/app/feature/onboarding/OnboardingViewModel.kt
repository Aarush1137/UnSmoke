package com.unsmoke.app.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.QuitAttemptEntity
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

data class OnboardingState(
    val step: Int = 0,
    val quitDate: LocalDate = LocalDate.now(),
    val cigarettesPerDay: String = "",
    val cigarettesPerPack: String = "20",
    val packPrice: String = "",
    val userName: String = "",
    val isComplete: Boolean = false
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun updateStep(step: Int) {
        _uiState.update { it.copy(step = step) }
    }

    fun updateQuitDate(date: LocalDate) {
        _uiState.update { it.copy(quitDate = date) }
    }

    fun updateCigarettesPerDay(value: String) {
        _uiState.update { it.copy(cigarettesPerDay = value) }
    }

    fun updatePackPrice(value: String) {
        _uiState.update { it.copy(packPrice = value) }
    }
    
    fun updateUserName(name: String) {
        _uiState.update { it.copy(userName = name) }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            val state = _uiState.value
            
            // Calculate price per cigarette
            val packPriceDouble = state.packPrice.toDoubleOrNull() ?: 0.0
            val perPackInt = state.cigarettesPerPack.toIntOrNull() ?: 20
            val pricePerCig = if (perPackInt > 0) packPriceDouble / perPackInt else 0.0

            val startEpoch = state.quitDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

            val attempt = QuitAttemptEntity(
                startEpochMillis = startEpoch,
                cigarettesPerDay = state.cigarettesPerDay.toDoubleOrNull() ?: 0.0, endEpochMillis = null, status = "ACTIVE", cigarettesPerPack = state.cigarettesPerPack.toIntOrNull() ?: 20, packPrice = state.packPrice.toDoubleOrNull() ?: 0.0, timezone = ZoneId.systemDefault().id, createdAt = System.currentTimeMillis(),
                pricePerCigarette = pricePerCig,
                
                
            )
            
            quitAttemptRepo.insertAttempt(attempt)
            // dataStore.setUserName(state.userName)
            dataStore.setOnboardingComplete(true)
            
            _uiState.update { it.copy(isComplete = true) }
        }
    }
}
