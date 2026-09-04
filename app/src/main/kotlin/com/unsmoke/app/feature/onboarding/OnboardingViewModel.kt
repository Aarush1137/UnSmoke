package com.unsmoke.app.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.NRTProductEntity
import com.unsmoke.app.core.data.database.entity.QuitAttemptEntity
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

data class OnboardingState(
    val step: Int = 0,
    val alreadyQuit: Boolean? = null,       // null = not answered yet
    val quitDate: LocalDate = LocalDate.now(),
    val cigarettesPerDay: String = "",
    val cigarettesPerPack: String = "20",
    val packPrice: String = "",
    val triggers: Set<String> = emptySet(),
    val supports: Set<String> = emptySet(),
    val nrtProduct: String = "NONE",
    val breathHoldTime: Int = 0,
    val quitReason: String = "",
    val userName: String = "",
    val shortTermGoal: String = "",         // e.g. "Survive the first week"
    val longTermGoal: String = "",          // e.g. "Run a 5K without wheezing"
    val isComplete: Boolean = false,
    val substanceType: String = "CIGARETTE",
    val nicotineStrengthMg: String = ""
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val nrtRepo: NRTRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun updateStep(step: Int) = _uiState.update { it.copy(step = step) }
    fun updateQuitDate(date: LocalDate) = _uiState.update { it.copy(quitDate = date) }
    fun updateSubstanceType(type: String) = _uiState.update { it.copy(substanceType = type) }
    fun updateNicotineStrength(mg: String) = _uiState.update { it.copy(nicotineStrengthMg = mg) }
    fun updateCigarettesPerDay(value: String) = _uiState.update { it.copy(cigarettesPerDay = value) }
    fun updateCigarettesPerPack(value: String) = _uiState.update { it.copy(cigarettesPerPack = value) }
    fun updatePackPrice(value: String) = _uiState.update { it.copy(packPrice = value) }
    fun updateUserName(name: String) = _uiState.update { it.copy(userName = name) }
    fun updateQuitReason(reason: String) = _uiState.update { it.copy(quitReason = reason) }
    fun updateNrtProduct(product: String) = _uiState.update { it.copy(nrtProduct = product) }
    fun updateShortTermGoal(goal: String) = _uiState.update { it.copy(shortTermGoal = goal) }
    fun updateLongTermGoal(goal: String) = _uiState.update { it.copy(longTermGoal = goal) }

    fun setAlreadyQuit(already: Boolean) {
        _uiState.update {
            it.copy(
                alreadyQuit = already,
                quitDate = if (already) it.quitDate else LocalDate.now()
            )
        }
    }

    fun toggleTrigger(trigger: String) {
        _uiState.update { state ->
            state.copy(triggers = state.triggers.toMutableSet().apply {
                if (!add(trigger)) remove(trigger)
            })
        }
    }

    fun toggleSupport(support: String) {
        _uiState.update { state ->
            state.copy(supports = state.supports.toMutableSet().apply {
                if (!add(support)) remove(support)
            })
        }
    }

    fun updateBreathHoldTime(time: Int) {
        _uiState.update { it.copy(breathHoldTime = time) }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            val state = _uiState.value
            val perPackInt = state.cigarettesPerPack.toIntOrNull() ?: 20
            val unitPriceDouble = state.packPrice.toDoubleOrNull() ?: 0.0
            
            val rawConsumption = state.cigarettesPerDay.toDoubleOrNull() ?: 0.0
            val dailyRate = if (state.substanceType == "VAPING") rawConsumption / 7.0 else rawConsumption
            
            val pricePerCig = unitPriceDouble
            val calculatedPackPrice = unitPriceDouble * perPackInt
            val startEpoch = if (state.quitDate.isEqual(LocalDate.now())) {
                System.currentTimeMillis()
            } else {
                state.quitDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }

            quitAttemptRepo.insertAttempt(
                QuitAttemptEntity(
                    startEpochMillis = startEpoch,
                    cigarettesPerDay = dailyRate,
                    endEpochMillis = null,
                    status = "ACTIVE",
                    cigarettesPerPack = perPackInt,
                    packPrice = calculatedPackPrice,
                    substanceType = state.substanceType,
                    nicotineStrengthMg = state.nicotineStrengthMg.toDoubleOrNull(),
                    pricePerCigarette = pricePerCig,
                    timezone = ZoneId.systemDefault().id,
                    createdAt = System.currentTimeMillis()
                )
            )

            dataStore.setUserName(state.userName.ifBlank { "Champion" })
            dataStore.setQuitReason(state.quitReason.ifBlank { "Better health and freedom" })
            dataStore.setPersonalPlan(state.triggers, state.supports, state.nrtProduct)
            dataStore.setBreathHold(state.breathHoldTime, state.breathHoldTime)
            dataStore.setGoals(
                state.shortTermGoal.ifBlank { "Get through the first 72 hours" },
                state.longTermGoal.ifBlank { "Live smoke-free for 1 year" }
            )

            // Nicotex is seeded only as a tracking product. The app never suggests a dose.
            if (state.nrtProduct == "NICOTEX_GUM" && nrtRepo.getProducts().first().none { it.name == "Nicotex Gum" }) {
                nrtRepo.saveProduct(
                    NRTProductEntity(
                        type = "GUM",
                        name = "Nicotex Gum",
                        nicotineStrengthMg = 2.0,
                        packPrice = 80.0,
                        unitsPerPack = 9,
                        pricePerUnit = 8.89
                    )
                )
            }

            dataStore.setOnboardingComplete(true)
            _uiState.update { it.copy(isComplete = true) }
        }
    }
}
