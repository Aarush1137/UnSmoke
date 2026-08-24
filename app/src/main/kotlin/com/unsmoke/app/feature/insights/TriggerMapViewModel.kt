package com.unsmoke.app.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import com.unsmoke.app.core.data.database.entity.QuitAttemptEntity
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TriggerMapViewModel @Inject constructor(
    quitAttemptRepo: QuitAttemptRepository,
    cravingRepo: CravingRepository
) : ViewModel() {

    val cravingsWithLocation: StateFlow<List<CravingEventEntity>> = quitAttemptRepo.getActiveAttempt()
        .flatMapLatest { attempt: QuitAttemptEntity? ->
            if (attempt == null) flowOf(emptyList())
            else cravingRepo.getCravings(attempt.id).map { list: List<CravingEventEntity> ->
                list.filter { it.latitude != null && it.longitude != null }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}