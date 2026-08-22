package com.unsmoke.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class HomeUiState(
    val userName: String? = "User",
    val smokeFreeDays: Int = 0,
    val quitDateDisplay: String = "Loading...",
    val netMoneySaved: Double = 0.0,
    val currentQuote: String = "Stay strong!"
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    val days = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                    val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay)
                    val saved = CalculationEngine.grossMoneySaved(avoided, attempt.pricePerCigarette)
                    
                    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                    val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))

                    _uiState.update { 
                        it.copy(
                            smokeFreeDays = days,
                            netMoneySaved = saved,
                            quitDateDisplay = dateStr
                        ) 
                    }
                }
            }
        }
    }
}
