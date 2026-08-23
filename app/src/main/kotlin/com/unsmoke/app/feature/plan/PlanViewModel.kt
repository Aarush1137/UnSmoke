package com.unsmoke.app.feature.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class PlanUiState(
    val quitReason: String = "",
    val shortTermGoal: String = "",
    val longTermGoal: String = "",
    val triggers: Set<String> = emptySet(),
    val supports: Set<String> = emptySet(),
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = ""
)

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {
    
    private data class PlanData1(val reason: String, val shortGoal: String, val longGoal: String)
    private data class PlanData2(val triggers: Set<String>, val supports: Set<String>, val name: String, val phone: String)
    
    val uiState: StateFlow<PlanUiState> = combine(
        combine(dataStore.quitReason, dataStore.shortTermGoal, dataStore.longTermGoal) { r, s, l ->
            PlanData1(r, s, l)
        },
        combine(dataStore.planTriggers, dataStore.planSupports, dataStore.emergencyContactName, dataStore.emergencyContactPhone) { t, s, n, p ->
            PlanData2(t, s, n, p)
        }
    ) { d1, d2 ->
        PlanUiState(
            quitReason = d1.reason,
            shortTermGoal = d1.shortGoal,
            longTermGoal = d1.longGoal,
            triggers = d2.triggers,
            supports = d2.supports,
            emergencyContactName = d2.name,
            emergencyContactPhone = d2.phone
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PlanUiState())
}