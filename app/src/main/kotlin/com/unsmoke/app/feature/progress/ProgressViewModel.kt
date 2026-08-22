package com.unsmoke.app.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProgressUiState(
    val smokeFreeDays: Int = 0,
    val cigarettesAvoided: Int = 0,
    val moneySaved: Double = 0.0,
    val cravingsDefeated: Int = 0,
    val nrtLogged: Int = 0,
    val timeFilter: String = "7 Days",
    val currencySymbol: String = "$"
)

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                quitAttemptRepo.getActiveAttempt(),
                dataStore.currencySymbol
            ) { attempt, currency ->
                if (attempt != null) {
                    val days = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                    val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay).toInt()
                    val saved = CalculationEngine.grossMoneySaved(avoided.toDouble(), attempt.pricePerCigarette)
                    
                    _uiState.update { 
                        it.copy(
                            smokeFreeDays = days,
                            cigarettesAvoided = avoided,
                            moneySaved = saved,
                            currencySymbol = currency ?: "$"
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(currencySymbol = currency ?: "$")
                    }
                }
            }.collect {}
        }
    }

    fun setTimeFilter(filter: String) {
        _uiState.update { it.copy(timeFilter = filter) }
        // TODO: filter metrics based on time window
    }
}
