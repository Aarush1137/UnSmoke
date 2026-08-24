package com.unsmoke.app.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.PersonalizationEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.data.repository.TitrationRepository
import com.unsmoke.app.core.data.database.entity.TitrationLogEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InsightsUiState(
    val topTrigger: String = "Not enough data",
    val highRiskTime: String = "Not enough data",
    val bestCopingStrategy: String = "Not enough data",
    val successRate: Int = 0,
    val isLoading: Boolean = true,
    val hasData: Boolean = false,
    val usesNRT: Boolean = false,
    val isVaping: Boolean = false,
    val currentNicotineStrengthMg: Double? = null,
    val titrationLogs: List<TitrationLogEntity> = emptyList(),
    val elapsedMillis: Long = 0L
)

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val cravingRepo: CravingRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val nrtRepo: NRTRepository,
    private val titrationRepo: TitrationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    val currentElapsed = System.currentTimeMillis() - attempt.startEpochMillis
                    val nrtUsages = nrtRepo.getUsage(attempt.id).firstOrNull() ?: emptyList()
                    val hasNRT = nrtUsages.isNotEmpty()
                    
                    titrationRepo.getLogsForAttempt(attempt.id).collect { logs ->
                        val currentMg = logs.lastOrNull()?.nicotineStrengthMg ?: attempt.nicotineStrengthMg
                        _uiState.update { it.copy(
                            elapsedMillis = currentElapsed,
                            isVaping = attempt.substanceType == "VAPING",
                            currentNicotineStrengthMg = currentMg,
                            titrationLogs = logs
                        ) }
                    }
                    cravingRepo.getCravings(attempt.id).collect { cravings ->
                        if (cravings.isNotEmpty()) {
                            // Calculate Top Trigger
                            val triggers = cravings.mapNotNull { it.trigger }.flatMap { it.split(",") }.filter { it.isNotBlank() }
                            val topTrigger = triggers.groupingBy { it.trim() }.eachCount().maxByOrNull { it.value }?.key ?: "Unknown"
                            
                            // High Risk Time
                            val highRiskTime = PersonalizationEngine.getHighRiskTimeWindow(cravings) ?: "Not enough data"
                            
                            // Coping Strategy
                            val bestStrategy = PersonalizationEngine.getRankedCopingStrategies(cravings).firstOrNull() ?: "None logged yet"
                            
                            // Success Rate
                            val defeated = cravings.count { it.outcome == "DEFEATED" }
                            val rate = ((defeated.toDouble() / cravings.size) * 100).toInt()
                            
                            _uiState.update { 
                                it.copy(
                                    usesNRT = hasNRT,
                                    topTrigger = topTrigger,
                                    highRiskTime = highRiskTime,
                                    bestCopingStrategy = bestStrategy,
                                    successRate = rate,
                                    isLoading = false,
                                    hasData = true
                                )
                            }
                        } else {
                            _uiState.update { it.copy(isLoading = false, usesNRT = hasNRT) }
                        }
                    }
                }
            }
        }
    }

    fun logTitrationDrop(mg: Double) {
        viewModelScope.launch {
            val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
            if (attempt != null) {
                titrationRepo.insertLog(TitrationLogEntity(
                    quitAttemptId = attempt.id,
                    nicotineStrengthMg = mg,
                    timestamp = System.currentTimeMillis(),
                    notes = "Stepped down"
                ))
            }
        }
    }
}