package com.unsmoke.app.feature.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.DailyCheckInEntity
import com.unsmoke.app.core.domain.repository.CheckInRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JournalUiState(
    val checkIns: List<DailyCheckInEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val checkInRepo: CheckInRepository,
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(JournalUiState())
    val uiState: StateFlow<JournalUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    checkInRepo.getAllCheckIns().collect { logs ->
                        _uiState.update { it.copy(checkIns = logs, isLoading = false) }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

