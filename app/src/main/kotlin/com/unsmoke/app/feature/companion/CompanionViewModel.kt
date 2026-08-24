package com.unsmoke.app.feature.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.CompanionEntity
import com.unsmoke.app.core.data.repository.CompanionRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CompanionUiState(
    val companion: CompanionEntity? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class CompanionViewModel @Inject constructor(
    private val companionRepo: CompanionRepository,
    private val attemptRepo: QuitAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompanionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            attemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    companionRepo.getCompanion(attempt.id).collect { companion ->
                        if (companion == null) {
                            // Adopt a new companion!
                            val newCompanion = CompanionEntity(
                                quitAttemptId = attempt.id,
                                name = "Lungie", // Default cute name
                                health = 100,
                                stage = 0, // Egg / Sprout
                                lastInteractionTime = System.currentTimeMillis(),
                                mood = "HAPPY"
                            )
                            companionRepo.insertCompanion(newCompanion)
                        } else {
                            _uiState.update { it.copy(companion = companion, isLoading = false) }
                        }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun feedCompanion() {
        val current = _uiState.value.companion ?: return
        viewModelScope.launch {
            val newHealth = (current.health + 10).coerceAtMost(100)
            companionRepo.updateCompanion(current.copy(health = newHealth, mood = "HAPPY", lastInteractionTime = System.currentTimeMillis()))
        }
    }
}