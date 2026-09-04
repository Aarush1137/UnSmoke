package com.unsmoke.app.feature.achievements

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.engine.CalculationEngine
import com.unsmoke.app.core.domain.engine.ShareEngine
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.Instant

data class AchievementUiModel(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isUnlocked: Boolean = false
)

data class AchievementsUiState(
    val achievements: List<AchievementUiModel> = emptyList()
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().flatMapLatest { attempt ->
                if (attempt == null) {
                    flowOf(emptyList<AchievementUiModel>())
                } else {
                    cravingRepo.getCravings(attempt.id).map { attemptCravings ->
                        val now = Instant.now().toEpochMilli()
                        val daysFree = (now - attempt.startEpochMillis) / (1000L * 60 * 60 * 24)
                        val cravingsDefeated = attemptCravings.count { it.outcome == "DEFEATED" || it.outcome == "SURVIVED" }

                        listOf(
                            AchievementUiModel("24h", "24 Hours Free", "Survive the first day", Icons.Rounded.LooksOne, daysFree >= 1),
                            AchievementUiModel("3d", "3 Days Free", "Nicotine withdrawal peaks", Icons.Rounded.Looks3, daysFree >= 3),
                            AchievementUiModel("1w", "1 Week Free", "A whole week clean", Icons.Rounded.Event, daysFree >= 7),
                            AchievementUiModel("1m", "1 Month Free", "A major milestone", Icons.Rounded.CalendarMonth, daysFree >= 30),
                            AchievementUiModel("10_cravings", "Urge Surfer", "Defeat 10 cravings", Icons.Rounded.Waves, cravingsDefeated >= 10),
                            AchievementUiModel("50_cravings", "Craving Crusher", "Defeat 50 cravings", Icons.Rounded.Shield, cravingsDefeated >= 50),
                            AchievementUiModel("100_cravings", "Iron Will", "Defeat 100 cravings", Icons.Rounded.Whatshot, cravingsDefeated >= 100)
                        )
                    }
                }
            }.collect { achievements ->
                _uiState.update { it.copy(achievements = achievements) }
            }
        }
    }


}