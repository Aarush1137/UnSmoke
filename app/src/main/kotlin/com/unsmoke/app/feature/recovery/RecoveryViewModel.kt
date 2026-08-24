package com.unsmoke.app.feature.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.SmokingEventEntity
import com.unsmoke.app.core.data.database.entity.QuitAttemptEntity
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.data.repository.CompanionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.ZoneId

data class RecoveryUiState(
    val step: Int = 1,
    val cigarettesSmoked: Int = 1,
    val trigger: String = "",
    val emotion: String = "",
    val isComplete: Boolean = false
)

@HiltViewModel
class RecoveryViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository,
    private val companionRepo: CompanionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecoveryUiState())
    val uiState: StateFlow<RecoveryUiState> = _uiState.asStateFlow()

    fun updateCigarettes(count: Int) = _uiState.update { it.copy(cigarettesSmoked = count) }
    fun updateTrigger(trigger: String) = _uiState.update { it.copy(trigger = trigger) }
    fun updateEmotion(emotion: String) = _uiState.update { it.copy(emotion = emotion) }
    fun nextStep() = _uiState.update { it.copy(step = it.step + 1) }

    fun finishRecovery(resetStreak: Boolean) {
        viewModelScope.launch {
            val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
            if (attempt != null) {
                if (resetStreak) {
                    val updated = attempt.copy(status = "RELAPSED", endEpochMillis = System.currentTimeMillis())
                    quitAttemptRepo.insertAttempt(updated)
                    
                    // Create a new quit attempt starting now
                    val newAttempt = QuitAttemptEntity(
                        startEpochMillis = System.currentTimeMillis(),
                        cigarettesPerDay = attempt.cigarettesPerDay,
                        endEpochMillis = null,
                        status = "ACTIVE",
                        cigarettesPerPack = attempt.cigarettesPerPack,
                        packPrice = attempt.packPrice,
                        timezone = ZoneId.systemDefault().id,
                        createdAt = System.currentTimeMillis(),
                        pricePerCigarette = attempt.pricePerCigarette
                    )
                    val newId = quitAttemptRepo.insertAttempt(newAttempt)
                    
                    companionRepo.getCompanion(attempt.id).firstOrNull()?.let { oldComp ->
                        val newHealth = (oldComp.health - 25).coerceAtLeast(0)
                        val damagedComp = oldComp.copy(
                            id = 0, // Generate new row
                            quitAttemptId = newId,
                            health = newHealth,
                            mood = if (newHealth < 50) "SAD" else "NEUTRAL"
                        )
                        companionRepo.insertCompanion(damagedComp)
                    }
                }
            }
            _uiState.update { it.copy(isComplete = true) }
        }
    }
}
