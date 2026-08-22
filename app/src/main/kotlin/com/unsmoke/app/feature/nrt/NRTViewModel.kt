package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.NRTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NRTUiState(
    val todayLogCount: Int = 0,
    val todayLogs: List<Any> = emptyList(),
    val totalExpenditure: Double = 0.0,
    val nrtType: String = "Nicotex Gum",
    val showLogSheet: Boolean = false
)

@HiltViewModel
class NRTViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(NRTUiState())
    val uiState: StateFlow<NRTUiState> = _uiState.asStateFlow()

    fun toggleLogSheet(show: Boolean) {
        _uiState.update { it.copy(showLogSheet = show) }
    }

    fun logNRT(cravingBefore: Int, cravingAfter: Int) {
        toggleLogSheet(false)
    }
}
