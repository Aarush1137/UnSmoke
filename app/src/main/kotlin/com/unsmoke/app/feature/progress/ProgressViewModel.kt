package com.unsmoke.app.feature.progress

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.Instant
import java.time.ZoneId

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.engine.ShareEngine
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProgressUiState(
    val smokeFreeDays: Int = 0,
    val cigarettesAvoided: Int = 0,
    val moneySaved: Double = 0.0,
    val cravingsDefeated: Int = 0,
    val nrtLogged: Int = 0,
    val timeFilter: String = "All",
    val baselineBreathHold: Int = 0,
    val currentBreathHold: Int = 0,
    val currencySymbol: String = "$",
    val showCheckInPrompt: Boolean = false
)

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    // Cache raw data for filtering
    private var cachedStartEpochMillis: Long = 0L
    private var cachedCigarettesPerDay: Double = 0.0
    private var cachedPricePerCigarette: Double = 0.0
    private var cachedCravingsDefeated: Int = 0
    private var cachedNrtLogged: Int = 0

    init {
        viewModelScope.launch {
            combine(
                quitAttemptRepo.getActiveAttempt(),
                dataStore.currencySymbol,
                dataStore.baselineBreathHold,
                dataStore.currentBreathHold,
                dataStore.lastCheckInDate
            ) { attempt, currency, baseline, current, lastCheckIn ->
                if (attempt != null) {
                    // Cache raw values for time filter recalculation
                    cachedStartEpochMillis = attempt.startEpochMillis
                    cachedCigarettesPerDay = attempt.cigarettesPerDay
                    cachedPricePerCigarette = attempt.pricePerCigarette

                    val days = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                    val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay).toInt()
                    val saved = CalculationEngine.grossMoneySaved(avoided.toDouble(), attempt.pricePerCigarette)
                    
                    _uiState.update { 
                        it.copy(
                            smokeFreeDays = days,
                            cigarettesAvoided = avoided,
                            moneySaved = saved,
                            currencySymbol = currency ?: "$",
                            baselineBreathHold = baseline,
                            currentBreathHold = current,
                            showCheckInPrompt = shouldShowPrompt(lastCheckIn, attempt.startEpochMillis)
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(currencySymbol = currency ?: "$",
                            baselineBreathHold = baseline,
                            currentBreathHold = current,
                            showCheckInPrompt = shouldShowPrompt(lastCheckIn, System.currentTimeMillis()))
                    }
                }
            }.collect {}
        }

        // Observe cravings and NRT for counts using flatMapLatest to cancel child collectors
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) {
                    kotlinx.coroutines.flow.flowOf(emptyList<com.unsmoke.app.core.data.database.entity.CravingEventEntity>() to emptyList<com.unsmoke.app.core.data.database.entity.NRTUsageEntity>())
                } else {
                    combine(
                        cravingRepo.getCravings(attempt.id),
                        nrtRepo.getUsage(attempt.id)
                    ) { cravings, usages ->
                        cravings to usages
                    }
                }
            }.collect { (cravings, usages) ->
                cachedCravingsDefeated = cravings.count { it.outcome == "DEFEATED" || it.outcome == "SURVIVED" }
                cachedNrtLogged = usages.sumOf { it.quantity }
                _uiState.update { it.copy(nrtLogged = cachedNrtLogged) }
                recalculateForFilter(_uiState.value.timeFilter, cravings = cravings)
            }
        }
    }

    fun shareMilestone(context: Context, currency: String) {
        val days = uiState.value.smokeFreeDays
        val money = uiState.value.moneySaved
        val uri = ShareEngine.generateShareImage(context, days, money, currency)
        if (uri != null) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val chooser = Intent.createChooser(intent, "Share Milestone")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(context, chooser, null)
        }
    }

    fun setTimeFilter(filter: String) {
        _uiState.update { it.copy(timeFilter = filter) }
        viewModelScope.launch {
            recalculateForFilter(filter)
        }
    }

    private fun recalculateForFilter(
        filter: String,
        cravings: List<com.unsmoke.app.core.data.database.entity.CravingEventEntity>? = null
    ) {
        if (cachedStartEpochMillis == 0L) return

        val now = System.currentTimeMillis()
        val filterStartMillis = when (filter) {
            "7 Days" -> now - 7L * 24 * 60 * 60 * 1000
            "30 Days" -> now - 30L * 24 * 60 * 60 * 1000
            else -> cachedStartEpochMillis // "All"
        }

        // Effective start is the later of quit start and filter start
        val effectiveStart = maxOf(cachedStartEpochMillis, filterStartMillis)

        val days = CalculationEngine.smokeFreeDuration(effectiveStart).toDays().toInt()
        val avoided = CalculationEngine.cigarettesAvoided(effectiveStart, cachedCigarettesPerDay).toInt()
        val saved = CalculationEngine.grossMoneySaved(avoided.toDouble(), cachedPricePerCigarette)

        // Filter cravings by time window if available
        val filteredDefeated = cravings?.count {
            it.timestamp >= filterStartMillis && (it.outcome == "DEFEATED" || it.outcome == "SURVIVED")
        } ?: cachedCravingsDefeated

        _uiState.update {
            it.copy(
                smokeFreeDays = days,
                cigarettesAvoided = avoided,
                moneySaved = saved,
                cravingsDefeated = filteredDefeated
            )
        }
    }

    private fun shouldShowPrompt(lastCheckIn: String, quitStartMillis: Long): Boolean {
        val today = LocalDate.now()
        val lastDate = if (lastCheckIn.isNotBlank()) {
            LocalDate.parse(lastCheckIn)
        } else {
            Instant.ofEpochMilli(quitStartMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        }
        return ChronoUnit.DAYS.between(lastDate, today) >= 7
    }

    fun submitCheckIn(breathHoldTime: Int) {
        viewModelScope.launch {
            val baseline = dataStore.baselineBreathHold.first()
            dataStore.setBreathHold(baseline, breathHoldTime)
            dataStore.setLastCheckInDate(LocalDate.now().toString())
        }
    }

    fun dismissCheckIn() {
        viewModelScope.launch {
            dataStore.setLastCheckInDate(LocalDate.now().toString())
        }
    }
}