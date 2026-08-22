package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.NRTUsageEntity
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
    private val nrtRepo: NRTRepository,
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NRTUiState())
    val uiState: StateFlow<NRTUiState> = _uiState.asStateFlow()

    private var currentQuitAttemptId: Long = 1L

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    currentQuitAttemptId = attempt.id
                    val cigs = attempt.cigarettesPerDay.toInt()
                    val recommended = if (cigs > 15) 9 else if (cigs > 5) 5 else 2
                    val type = if (cigs > 15) "4mg Gum" else "2mg Gum"
                    
                    _uiState.update { 
                        it.copy(
                            recommendedDailyDoses = recommended,
                            nrtType = type
                        )
                    }

                    nrtRepo.getUsage(attempt.id).collect { usages ->
                        val now = System.currentTimeMillis()
                        val startOfDay = now - (now % 86400000) 
                        val todayCount = usages.count { it.timestamp >= startOfDay }
                        
                        _uiState.update { it.copy(todayLogCount = todayCount) }
                    }
                }
            }
        }
    }

    fun toggleLogSheet(show: Boolean) {
        _uiState.update { it.copy(showLogSheet = show) }
    }

    fun logNRT(cravingBefore: Int, cravingAfter: Int) {
        viewModelScope.launch {
            val usage = NRTUsageEntity(
                productId = 1L,
                quitAttemptId = currentQuitAttemptId,
                timestamp = System.currentTimeMillis(),
                quantity = 1,
                cravingBefore = cravingBefore,
                cravingAfter = cravingAfter,
                trigger = null,
                notes = null
            )
            nrtRepo.logUsage(usage)
            toggleLogSheet(false)
        }
    }
}
