package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.NRTProductEntity
import com.unsmoke.app.core.data.database.entity.NRTUsageEntity
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

data class NRTLogItem(
    val id: Long,
    val productName: String,
    val timestamp: Long,
    val quantity: Int,
    val cravingBefore: Int?,
    val cravingAfter: Int?,
    val trigger: String?
)

data class NRTUiState(
    val todayUnitCount: Int = 0,
    val totalUnitCount: Int = 0,
    val todayLogs: List<NRTLogItem> = emptyList(),
    val nrtType: String = "Nicotex Gum",
    val selectedProductId: Long? = null,
    val averageCravingBefore: Double? = null,
    val averageCravingAfter: Double? = null,
    val loggedCravingChange: Double? = null,
    val showLogSheet: Boolean = false,
    val recommendation: com.unsmoke.app.core.domain.engine.NRTTaperingEngine.TaperingRecommendation? = null
)

private data class NRTSource(
    val attempt: com.unsmoke.app.core.data.database.entity.QuitAttemptEntity?,
    
    val product: NRTProductEntity?,
    val usages: List<NRTUsageEntity>
)

@HiltViewModel
class NRTViewModel @Inject constructor(
    private val nrtRepo: NRTRepository,
    quitAttemptRepo: QuitAttemptRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NRTUiState())
    val uiState: StateFlow<NRTUiState> = _uiState.asStateFlow()

    private val activeAttempt = quitAttemptRepo.getActiveAttempt().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), null
    )

    init {
        viewModelScope.launch {
            combine(activeAttempt, nrtRepo.getProducts()) { attempt, products -> attempt to products }
                .flatMapLatest { (attempt, products) ->
                    if (attempt == null) flowOf(NRTSource(null, products.firstOrNull(), emptyList()))
                    else nrtRepo.getUsage(attempt.id).combine(flowOf(products)) { usages, availableProducts ->
                        val product = availableProducts.firstOrNull { it.isActive } ?: availableProducts.firstOrNull()
                        NRTSource(attempt, product, usages)
                    }
                }
                .collect { source -> _uiState.update { state -> source.toUiState(state.showLogSheet) } }
        }
    }

    fun toggleLogSheet(show: Boolean) = _uiState.update { it.copy(showLogSheet = show) }

    fun logNRT(timestamp: Long, quantity: Int, cravingBefore: Int, cravingAfter: Int, trigger: String?) {
        val state = _uiState.value
        val attemptId = activeAttempt.value?.id ?: return
        viewModelScope.launch {
            val productId = state.selectedProductId ?: ensureNicotexTrackingProduct()
            nrtRepo.logUsage(
                NRTUsageEntity(
                    productId = productId,
                    quitAttemptId = attemptId,
                    timestamp = timestamp,
                    quantity = quantity.coerceAtLeast(1),
                    cravingBefore = cravingBefore,
                    cravingAfter = cravingAfter,
                    trigger = trigger?.trim()?.takeIf { it.isNotEmpty() },
                    notes = null
                )
            )
            toggleLogSheet(false)
        }
    }

    private suspend fun ensureNicotexTrackingProduct(): Long {
        return nrtRepo.getProducts().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).value
            .firstOrNull { it.name == "Nicotex Gum" }?.id
            ?: nrtRepo.saveProduct(
                NRTProductEntity(
                    type = "GUM",
                    name = "Nicotex Gum",
                    nicotineStrengthMg = 2.0,
                    packPrice = 0.0,
                    unitsPerPack = 1,
                    pricePerUnit = 0.0
                )
            )
    }

    private fun NRTSource.toUiState(showLogSheet: Boolean): NRTUiState {
        val recommendation = attempt?.let { 
            val days = com.unsmoke.app.core.domain.engine.CalculationEngine.smokeFreeDays(it.startEpochMillis)
            com.unsmoke.app.core.domain.engine.NRTTaperingEngine.getNicotexGumPlan(days / 7, 2)
        }
        val startOfToday = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val today = usages.filter { it.timestamp >= startOfToday }
        val rated = usages.filter { it.cravingBefore != null && it.cravingAfter != null }
        val before = rated.mapNotNull { it.cravingBefore }.average().takeIf { !it.isNaN() }
        val after = rated.mapNotNull { it.cravingAfter }.average().takeIf { !it.isNaN() }
        return NRTUiState(
            todayUnitCount = today.sumOf { it.quantity },
            totalUnitCount = usages.sumOf { it.quantity },
            todayLogs = today.sortedByDescending { it.timestamp }.map {
                NRTLogItem(it.id, product?.name ?: "NRT", it.timestamp, it.quantity, it.cravingBefore, it.cravingAfter, it.trigger)
            },
            nrtType = product?.name ?: "Nicotex Gum",
            selectedProductId = product?.id,
            averageCravingBefore = before,
            averageCravingAfter = after,
            loggedCravingChange = if (before != null && after != null) before - after else null,
            showLogSheet = showLogSheet,
            recommendation = recommendation
        )
    }
}


