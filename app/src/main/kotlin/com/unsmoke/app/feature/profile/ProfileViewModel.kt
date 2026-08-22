package com.unsmoke.app.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class ProfileUiState(
    val userName: String = "User",
    val smokeFreeSince: String = "Not started yet",
    val longestStreak: Int = 0
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Note: In a real app we'd fetch userName from a UserProfileRepository or DataStore
            quitAttemptRepo.getActiveAttempt().collect { attempt ->
                if (attempt != null) {
                    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
                    val dateStr = formatter.format(Instant.ofEpochMilli(attempt.startEpochMillis))
                    
                    _uiState.update { 
                        it.copy(
                            userName = "My Profile", // Placeholder until DataStore is wired
                            smokeFreeSince = dateStr
                        )
                    }
                }
            }
        }
    }
}
