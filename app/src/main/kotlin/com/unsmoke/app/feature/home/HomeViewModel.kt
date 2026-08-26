package com.unsmoke.app.feature.home

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.firstOrNull

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.AiInsightsRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.engine.QuitCoachData
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class HomeUiState(
    val userName: String = "User",
    val smokeFreeDays: Int = 0,
    val startEpochMillis: Long? = null,
    val quitDateDisplay: String = "Loading...",
    val netMoneySaved: Double = 0.0,
    val currentQuote: String = "Stay strong!",
    val dailyLesson: String = "Did you know? Nicotine cravings usually only last 5 to 10 minutes.",
    val currencySymbol: String = "$",
    val aiInsight: String? = null,
    val isAiLoading: Boolean = false
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nrtRepo: NRTRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val aiRepo: AiInsightsRepository,
    private val dataStore: UserPreferencesDataStore,
    private val wearSyncManager: com.unsmoke.app.core.device.WearSyncManager,
    private val cloudBackupEngine: com.unsmoke.app.core.domain.engine.CloudBackupEngine
) : ViewModel() {

    init {
        viewModelScope.launch {
            cloudBackupEngine.syncLocalDataToCloud()
        }
    }
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) {
                    combine(dataStore.userName, dataStore.currencySymbol) { name, currency ->
                        HomeUiState(userName = name, currencySymbol = currency ?: "$")
                    }
                } else {
                    combine(
                        nrtRepo.getUsage(attempt.id),
                        nrtRepo.getProducts(),
                        dataStore.userName,
                        dataStore.currencySymbol
                    ) { usages, products, name, currency ->
                        val days = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                        val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay)
                        val grossSaved = CalculationEngine.grossMoneySaved(avoided, attempt.pricePerCigarette)
                        
                        val nrtCost = usages.sumOf { usage ->
                            val product = products.find { it.id == usage.productId }
                            if (product != null) (usage.quantity * product.pricePerUnit) else 0.0
                        }
                        
                        val saved = CalculationEngine.netMoneySaved(grossSaved, nrtCost)
                        
                        // Sync to Wear OS
                        viewModelScope.launch { wearSyncManager.syncQuitStatus(attempt.startEpochMillis) }
                        
                        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                        val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))
                        
                        _uiState.value.copy(
                            userName = name,
                            smokeFreeDays = days,
                            startEpochMillis = attempt.startEpochMillis,
                            quitDateDisplay = dateStr,
                            netMoneySaved = saved,
                            dailyLesson = QuitCoachData.getLessonForDay(days),
                            currencySymbol = currency ?: "$"
                        )
                    }
                }
            }.collect { state ->
                // Preserve AI insight when other state updates
                _uiState.value = state.copy(
                    aiInsight = _uiState.value.aiInsight,
                    isAiLoading = _uiState.value.isAiLoading
                )
            }
        }
    }

    fun fetchAiInsight() {
        viewModelScope.launch {
            _uiState.update { it.copy(isAiLoading = true) }
            val activeAttempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
            if (activeAttempt != null) {
                val cravings = cravingRepo.getCravings(activeAttempt.id).firstOrNull() ?: emptyList()
                aiRepo.generateRelapsePrediction(cravings).collect { insight ->
                    _uiState.update { it.copy(aiInsight = insight, isAiLoading = false) }
                }
            } else {
                _uiState.update { it.copy(aiInsight = "Log a quit attempt first.", isAiLoading = false) }
            }
        }
    }
}