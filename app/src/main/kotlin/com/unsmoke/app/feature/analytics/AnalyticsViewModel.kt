package com.unsmoke.app.feature.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

data class CravingAnalyticsState(
    val isLoading: Boolean = true,
    val totalCravings: Int = 0,
    val averageIntensity: Float = 0f,
    val topTriggers: List<Pair<String, Int>> = emptyList(),
    val cravingsByHour: Map<Int, Int> = emptyMap()
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val cravingRepo: CravingRepository,
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CravingAnalyticsState())
    val uiState: StateFlow<CravingAnalyticsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) flowOf(emptyList())
                else cravingRepo.getCravings(attempt.id)
            }.collect { cravings ->
                if (cravings.isEmpty()) {
                    _uiState.value = CravingAnalyticsState(isLoading = false)
                    return@collect
                }
                
                val avgIntensity = cravings.map { it.intensity }.average().toFloat()
                val triggers = cravings.mapNotNull { it.trigger }.filter { it.isNotBlank() }
                    .groupingBy { it }.eachCount().toList().sortedByDescending { it.second }.take(5)
                
                val byHour = cravings.groupBy { 
                    Instant.ofEpochMilli(it.timestamp).atZone(ZoneId.systemDefault()).hour 
                }.mapValues { it.value.size }
                
                _uiState.value = CravingAnalyticsState(
                    isLoading = false,
                    totalCravings = cravings.size,
                    averageIntensity = if (avgIntensity.isNaN()) 0f else avgIntensity,
                    topTriggers = triggers,
                    cravingsByHour = byHour
                )
            }
        }
    }
}
