package com.unsmoke.app.feature.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.AchievementBadge
import com.unsmoke.app.core.domain.engine.AchievementEngine
import com.unsmoke.app.core.domain.engine.BadgeState
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AchievementItemState(
    val badge: AchievementBadge,
    val state: BadgeState
)

data class AchievementsUiState(
    val badges: List<AchievementItemState> = emptyList(),
    val totalEarned: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState: StateFlow<AchievementsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                quitAttemptRepo.getActiveAttempt(),
                // If attempt is null, just pass an empty list of cravings
                quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                    if (attempt != null) cravingRepo.getCravings(attempt.id) else flowOf(emptyList())
                }
            ) { attempt, cravings ->
                val days = if (attempt != null) CalculationEngine.smokeFreeDays(attempt.startEpochMillis) else 0
                val defeated = cravings.count { it.outcome == "DEFEATED" }

                val itemStates = AchievementEngine.allBadges.map { badge ->
                    AchievementItemState(
                        badge = badge,
                        state = AchievementEngine.getBadgeState(badge, days, defeated)
                    )
                }

                val earnedCount = itemStates.count { it.state == BadgeState.EARNED }

                _uiState.update { 
                    it.copy(
                        badges = itemStates,
                        totalEarned = earnedCount,
                        isLoading = false
                    ) 
                }
            }.collect()
        }
    }
}
