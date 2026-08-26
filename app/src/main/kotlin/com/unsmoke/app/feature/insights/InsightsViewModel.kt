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
@kotlin.OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
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
            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) {
                    flowOf(InsightsUiState(isLoading = false))
                } else {
                    val currentElapsed = System.currentTimeMillis() - attempt.startEpochMillis
                    val isVaping = attempt.substanceType == "VAPING"

                    combine(
                        nrtRepo.getUsage(attempt.id),
                        titrationRepo.getLogsForAttempt(attempt.id),
                        cravingRepo.getCravings(attempt.id)
                    ) { nrtUsages, logs, cravings ->
                        val hasNRT = nrtUsages.isNotEmpty()
                        val currentMg = logs.lastOrNull()?.nicotineStrengthMg ?: attempt.nicotineStrengthMg

                        if (cravings.isNotEmpty()) {
                            val triggers = cravings.mapNotNull { it.trigger }.flatMap { it.split(",") }.filter { it.isNotBlank() }
                            val topTrigger = triggers.groupingBy { it.trim() }.eachCount().maxByOrNull { it.value }?.key ?: "Unknown"
                            val highRiskTime = PersonalizationEngine.getHighRiskTimeWindow(cravings) ?: "Not enough data"
                            val bestStrategy = PersonalizationEngine.getRankedCopingStrategies(cravings).firstOrNull() ?: "None logged yet"
                            
                            val defeated = cravings.count { it.outcome == "DEFEATED" }
                            val rate = ((defeated.toDouble() / cravings.size) * 100).toInt()

                            InsightsUiState(
                                usesNRT = hasNRT,
                                topTrigger = topTrigger,
                                highRiskTime = highRiskTime,
                                bestCopingStrategy = bestStrategy,
                                successRate = rate,
                                isLoading = false,
                                hasData = true,
                                isVaping = isVaping,
                                currentNicotineStrengthMg = currentMg,
                                titrationLogs = logs,
                                elapsedMillis = currentElapsed
                            )
                        } else {
                            InsightsUiState(
                                isLoading = false,
                                usesNRT = hasNRT,
                                hasData = false,
                                isVaping = isVaping,
                                currentNicotineStrengthMg = currentMg,
                                titrationLogs = logs,
                                elapsedMillis = currentElapsed
                            )
                        }
                    }
                }
            }.collect { newState ->
                _uiState.value = newState
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