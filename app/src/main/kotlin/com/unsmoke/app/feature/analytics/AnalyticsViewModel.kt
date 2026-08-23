package com.unsmoke.app.feature.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.repository.HealthConnectRepository
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

data class CravingAnalyticsState(
    val isLoading: Boolean = true,
    val totalCravings: Int = 0,
    val averageIntensity: Float = 0f,
    val topTriggers: List<Pair<String, Int>> = emptyList(),
    val cravingsByHour: Map<Int, Int> = emptyMap(),
    val isHealthConnectAvailable: Boolean = false,
    val averageHeartRate: Long? = null
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val cravingRepo: CravingRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val healthRepo: HealthConnectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CravingAnalyticsState())
    val uiState: StateFlow<CravingAnalyticsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val isHcAvailable = healthRepo.isAvailable()
            var avgHr: Long? = null
            
            if (isHcAvailable) {
                try {
                    val now = Instant.now()
                    val start = now.minus(7, ChronoUnit.DAYS)
                    val hrRecords = healthRepo.getRestingHeartRateRecords(start, now)
                    if (hrRecords.isNotEmpty()) {
                        avgHr = hrRecords.map { it.beatsPerMinute }.average().toLong()
                    }
                } catch (e: Exception) {
                    // Ignore missing permissions in init
                }
            }

            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) flowOf(emptyList())
                else cravingRepo.getCravings(attempt.id)
            }.collect { cravings ->
                if (cravings.isEmpty()) {
                    _uiState.value = CravingAnalyticsState(
                        isLoading = false,
                        isHealthConnectAvailable = isHcAvailable,
                        averageHeartRate = avgHr
                    )
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
                    cravingsByHour = byHour,
                    isHealthConnectAvailable = isHcAvailable,
                    averageHeartRate = avgHr
                )
            }
        }
    }
}