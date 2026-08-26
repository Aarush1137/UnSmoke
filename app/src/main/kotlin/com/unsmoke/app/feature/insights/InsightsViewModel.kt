package com.unsmoke.app.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.PersonalizationEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.data.repository.TitrationRepository
import com.unsmoke.app.core.data.database.entity.TitrationLogEntity
import com.unsmoke.app.core.domain.repository.AiInsightsRepository
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
    val elapsedMillis: Long = 0L,
    val aiInsight: String? = null,
    val isAiLoading: Boolean = false
)

@HiltViewModel
@kotlin.OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class InsightsViewModel @Inject constructor(
    private val cravingRepo: CravingRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val nrtRepo: NRTRepository,
    private val titrationRepo: TitrationRepository,
    private val aiRepo: AiInsightsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    private var aiGeneratedForCurrentData = false

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

                        // Trigger AI Generation ONCE
                        if (!aiGeneratedForCurrentData && (cravings.isNotEmpty() || hasNRT)) {
                            aiGeneratedForCurrentData = true
                            generateAiInsight(cravings, nrtUsages, (currentElapsed / (1000 * 60 * 60 * 24)).toInt())
                        }

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
                                elapsedMillis = currentElapsed,
                                aiInsight = _uiState.value.aiInsight,
                                isAiLoading = _uiState.value.isAiLoading
                            )
                        } else {
                            InsightsUiState(
                                isLoading = false,
                                usesNRT = hasNRT,
                                hasData = false,
                                isVaping = isVaping,
                                currentNicotineStrengthMg = currentMg,
                                titrationLogs = logs,
                                elapsedMillis = currentElapsed,
                                aiInsight = _uiState.value.aiInsight,
                                isAiLoading = _uiState.value.isAiLoading
                            )
                        }
                    }
                }
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    private fun generateAiInsight(
        cravings: List<com.unsmoke.app.core.data.database.entity.CravingEventEntity>,
        nrtUsages: List<com.unsmoke.app.core.data.database.entity.NRTUsageEntity>,
        daysSmokeFree: Int
    ) {
        _uiState.update { it.copy(isAiLoading = true) }
        viewModelScope.launch {
            aiRepo.generateComprehensiveInsight(cravings, nrtUsages, daysSmokeFree).collect { insight ->
                _uiState.update { it.copy(aiInsight = insight, isAiLoading = false) }
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