package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.NRTEventEntity
import com.unsmoke.app.core.domain.repository.NRTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NRTUiState(
    val todayLogCount: Int = 0,
    val todayLogs: List<NRTEventEntity> = emptyList(),
    val totalExpenditure: Double = 0.0,
    val nrtType: String = "Nicotex Gum",
    val showLogSheet: Boolean = false
)

@HiltViewModel
class NRTViewModel @Inject constructor(
    private val nrtRepo: NRTRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NRTUiState())
    val uiState: StateFlow<NRTUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            nrtRepo.getTodayUsageCount().collect { count ->
                _uiState.update { it.copy(todayLogCount = count) }
            }
        }
    }

    fun toggleLogSheet(show: Boolean) {
        _uiState.update { it.copy(showLogSheet = show) }
    }

    fun logNRT(cravingBefore: Int, cravingAfter: Int) {
        viewModelScope.launch {
            val event = NRTEventEntity(
                timestampMillis = System.currentTimeMillis(),
                productName = _uiState.value.nrtType,
                quantity = 1,
                cravingBefore = cravingBefore,
                cravingAfter = cravingAfter
            )
            nrtRepo.insertUsage(event)
            toggleLogSheet(false)
        }
    }
}
