package com.unsmoke.app.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ProgressUiState(
    val smokeFreeDays: Int = 0,
    val cigarettesAvoided: Int = 0,
    val moneySaved: Double = 0.0,
    val cravingsDefeated: Int = 0,
    val nrtLogged: Int = 0,
    val timeFilter: String = "7 Days"
)

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()
}
