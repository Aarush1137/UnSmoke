package com.unsmoke.app.feature.craving

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import com.unsmoke.app.core.domain.repository.CravingRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CravingStep { INTENSITY, TRIGGER, NEED, TIMER, OUTCOME, LAPSE }

data class CravingUiState(
    val step: CravingStep = CravingStep.INTENSITY,
    val intensity: Int = 5,
    val selectedTriggers: Set<String> = emptySet(),
    val timerSeconds: Int = 600,
    val isTimerRunning: Boolean = false
)

@HiltViewModel
class CravingViewModel @Inject constructor(
    private val cravingRepo: CravingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CravingUiState())
    val uiState: StateFlow<CravingUiState> = _uiState.asStateFlow()

    fun updateIntensity(intensity: Int) {
        _uiState.update { it.copy(intensity = intensity) }
    }

    fun toggleTrigger(trigger: String) {
        _uiState.update { state ->
            val newTriggers = state.selectedTriggers.toMutableSet()
            if (newTriggers.contains(trigger)) newTriggers.remove(trigger) else newTriggers.add(trigger)
            state.copy(selectedTriggers = newTriggers)
        }
    }

    fun proceedToTrigger() = _uiState.update { it.copy(step = CravingStep.TRIGGER) }
    fun proceedToNeed() = _uiState.update { it.copy(step = CravingStep.NEED) }
    fun startTimer() {
        _uiState.update { it.copy(step = CravingStep.TIMER, isTimerRunning = true) }
        // We handle the countdown in the UI layer with LaunchedEffect for simplicity in this scaffold
    }

    fun resolveCraving(outcome: String, applicationContext: android.content.Context) {
        viewModelScope.launch {
            val state = _uiState.value
            val event = CravingEventEntity(
                quitAttemptId = 1L, timestamp = System.currentTimeMillis(),
                intensity = state.intensity,
                trigger = state.selectedTriggers.joinToString(","), location = null, intervention = null,
                outcome = outcome,
                durationSeconds = null, nrtUsedBefore = false, mood = null
            )
            cravingRepo.logCraving(event)
            
            
            if (outcome == "DEFEATED") {
                _uiState.update { it.copy(step = CravingStep.OUTCOME) }
            } else {
                _uiState.update { it.copy(step = CravingStep.LAPSE) }
            }
        }
    }
}
