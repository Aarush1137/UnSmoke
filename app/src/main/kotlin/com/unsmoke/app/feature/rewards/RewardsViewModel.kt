package com.unsmoke.app.feature.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.RewardGoalEntity
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.RewardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RewardsUiState(
    val isLoading: Boolean = true,
    val netMoneySaved: Double = 0.0,
    val currencySymbol: String = "\$",
    val goals: List<RewardGoalEntity> = emptyList()
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val rewardRepository: RewardRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val nrtRepo: NRTRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    val uiState: StateFlow<RewardsUiState> = quitAttemptRepo.getActiveAttempt()
        .flatMapLatest { attempt ->
            if (attempt == null) {
                flowOf(RewardsUiState(isLoading = false))
            } else {
                combine(
                    rewardRepository.getAllGoals(),
                    nrtRepo.getProducts(),
                    nrtRepo.getUsage(attempt.id),
                    dataStore.currencySymbol
                ) { goals: List<RewardGoalEntity>, products: List<com.unsmoke.app.core.data.database.entity.NRTProductEntity>, usages: List<com.unsmoke.app.core.data.database.entity.NRTUsageEntity>, currency: String? ->
                    val avoided = CalculationEngine.cigarettesAvoided(attempt.startEpochMillis, attempt.cigarettesPerDay)
                    val grossSaved = CalculationEngine.grossMoneySaved(avoided, attempt.pricePerCigarette)
                    
                    val nrtCost = usages.sumOf { usage ->
                        val product = products.find { it.id == usage.productId }
                        if (product != null) (usage.quantity * product.pricePerUnit) else 0.0
                    }
                    val saved = CalculationEngine.netMoneySaved(grossSaved, nrtCost)
                    
                    RewardsUiState(
                        isLoading = false,
                        netMoneySaved = saved,
                        currencySymbol = currency ?: "\$",
                        goals = goals
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RewardsUiState()
        )

    fun addGoal(name: String, targetAmount: Double) {
        viewModelScope.launch {
            rewardRepository.insertGoal(RewardGoalEntity(name = name, targetAmount = targetAmount, achievedAt = null))
        }
    }

    fun deleteGoal(goalId: Long) {
        viewModelScope.launch {
            rewardRepository.deleteGoal(goalId)
        }
    }
}