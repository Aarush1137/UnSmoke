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

import kotlin.math.roundToInt

data class HomeUiState(
    val userName: String = "User",
    val smokeFreeDays: Int = 0,
    val cigarettesAvoided: Int = 0,
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
                    val effectivePrice = if (attempt.pricePerCigarette < 5.0 && attempt.packPrice >= 10.0) {
                        val healedPrice = attempt.packPrice
                        val healedPackPrice = attempt.packPrice * attempt.cigarettesPerPack
                        launch {
                            quitAttemptRepo.insertAttempt(
                                attempt.copy(
                                    pricePerCigarette = healedPrice,
                                    packPrice = healedPackPrice
                                )
                            )
                        }
                        healedPrice
                    } else {
                        attempt.pricePerCigarette
                    }

                    combine(
                        nrtRepo.getUsage(attempt.id),
                        nrtRepo.getProducts(),
                        dataStore.userName,
                        dataStore.currencySymbol
                    ) { usages, products, name, currency ->
                        val days = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                        val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay)
                        val grossSaved = CalculationEngine.grossMoneySaved(avoided, effectivePrice)
                        
                        val nrtCost = usages.sumOf { usage ->
                            val product = products.find { it.id == usage.productId }
                            if (product != null) (usage.quantity * product.pricePerUnit) else 0.0
                        }
                        
                        val saved = CalculationEngine.netMoneySaved(grossSaved, nrtCost)
                        
                        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                        val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))
                        
                        HomeUiState(
                            userName = name,
                            smokeFreeDays = days,
                            cigarettesAvoided = avoided.roundToInt(),
                            startEpochMillis = attempt.startEpochMillis,
                            quitDateDisplay = dateStr,
                            netMoneySaved = saved,
                            dailyLesson = QuitCoachData.getLessonForDay(days),
                            currencySymbol = currency ?: "$"
                        )
                    }
                }
            }.collect { state ->
                state.startEpochMillis?.let { epoch ->
                    wearSyncManager.syncQuitStatus(epoch)
                }
                // Preserve AI insight and quotes when other state updates
                _uiState.update { current ->
                    state.copy(
                        aiInsight = current.aiInsight,
                        isAiLoading = current.isAiLoading,
                        currentQuote = current.currentQuote
                    )
                }
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