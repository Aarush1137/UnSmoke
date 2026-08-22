package com.unsmoke.app.feature.home

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
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
    val quitDateDisplay: String = "Loading...",
    val netMoneySaved: Double = 0.0,
    val currentQuote: String = "Stay strong!",
    val currencySymbol: String = "$"
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nrtRepo: NRTRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
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
                        
                        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                        val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))
                        
                        HomeUiState(
                            userName = name,
                            smokeFreeDays = days,
                            quitDateDisplay = dateStr,
                            netMoneySaved = saved,
                            currencySymbol = currency ?: "$"
                        )
                    }
                }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
