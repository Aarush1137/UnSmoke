package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NRTUiState(
    val todayLogCount: Int = 0,
    val recommendedDailyDoses: Int = 0,
    val todayLogs: List<Any> = emptyList(),
    val totalExpenditure: Double = 0.0,
    val nrtType: String = "Nicotex Gum",
    val showLogSheet: Boolean = false
)

@HiltViewModel
class NRTViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NRTUiState())
    val uiState: StateFlow<NRTUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    // Plan Generation Logic based on baseline:
                    // If smoked > 15 cigs, recommend 9 gums a day.
                    // If smoked <= 15 cigs, recommend 5 gums a day.
                    val cigs = attempt.cigarettesPerDay.toInt()
                    val recommended = if (cigs > 15) 9 else if (cigs > 5) 5 else 2
                    val type = if (cigs > 15) "4mg Gum" else "2mg Gum"
                    
                    _uiState.update { 
                        it.copy(
                            recommendedDailyDoses = recommended,
                            nrtType = type
                        )
                    }
                }
            }
        }
    }

    fun toggleLogSheet(show: Boolean) {
        _uiState.update { it.copy(showLogSheet = show) }
    }

    fun logNRT(cravingBefore: Int, cravingAfter: Int) {
        // Just increment log count for now until DB entities are wired
        _uiState.update { it.copy(
            todayLogCount = it.todayLogCount + 1,
            showLogSheet = false
        ) }
    }
}
