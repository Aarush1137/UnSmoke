package com.unsmoke.app.feature.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.DailyCheckInEntity
import com.unsmoke.app.core.domain.repository.CheckInRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.LocalDate

data class CheckInUiState(
    val mood: Int = 3, // 1-5
    val sleepQuality: Int = 3, // 1-5
    val stressLevel: Int = 3, // 1-5
    val weightKg: Double? = null,
    val notes: String = ""
)

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val checkInRepo: CheckInRepository,
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState: StateFlow<CheckInUiState> = _uiState.asStateFlow()

    fun updateMood(mood: Int) = _uiState.update { it.copy(mood = mood) }
    fun updateSleep(sleep: Int) = _uiState.update { it.copy(sleepQuality = sleep) }
    fun updateStress(stress: Int) = _uiState.update { it.copy(stressLevel = stress) }
    fun updateNotes(notes: String) = _uiState.update { it.copy(notes = notes) }

    fun submitCheckIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            
            // Map mood 1-5 to a rating string
            val rating = when (state.mood) {
                1 -> "Terrible"
                2 -> "Bad"
                3 -> "Okay"
                4 -> "Good"
                5 -> "Great"
                else -> "Okay"
            }

            val checkIn = DailyCheckInEntity(
                datestamp = LocalDate.now().toString(),
                dayRating = rating,
                smoked = false,
                cravingLevel = state.stressLevel,
                topHelper = null,
                tomorrowFocus = state.notes,
                timestamp = System.currentTimeMillis()
            )
            checkInRepo.saveCheckIn(checkIn)
            onSuccess()
        }
    }
}
