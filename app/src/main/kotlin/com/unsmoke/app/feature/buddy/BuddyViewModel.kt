package com.unsmoke.app.feature.buddy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.BuddyProfile
import com.unsmoke.app.core.domain.repository.BuddyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import javax.inject.Inject

data class BuddyUiState(
    val myProfile: BuddyProfile? = null,
    val buddyProfiles: List<BuddyProfile> = emptyList(),
    val pendingRequestProfiles: List<BuddyProfile> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isMockMode: Boolean = false
)

@HiltViewModel
class BuddyViewModel @Inject constructor(
    private val buddyRepo: BuddyRepository,
    private val userProfileDao: com.unsmoke.app.core.data.database.dao.UserProfileDao,
    private val quitAttemptRepo: com.unsmoke.app.core.domain.repository.QuitAttemptRepository,
    private val nrtRepo: com.unsmoke.app.core.domain.repository.NRTRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BuddyUiState())
    val uiState = _uiState.asStateFlow()
    
    private var buddiesJob: Job? = null
    private var requestsJob: Job? = null

    init {
        viewModelScope.launch {
            buddyRepo.isUsingMockFlow.collect { isMock ->
                _uiState.update { it.copy(isMockMode = isMock) }
            }
        }
        viewModelScope.launch {
            try {
                val myUid = buddyRepo.signInAnonymously()
                observeMyProfile(myUid)
                startStatsSyncLoop(myUid)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    @kotlin.OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private fun startStatsSyncLoop(myUid: String) {
        viewModelScope.launch {
            combine(
                userProfileDao.getProfile(),
                quitAttemptRepo.getActiveAttempt()
            ) { profile, attempt ->
                Pair(profile, attempt)
            }.flatMapLatest { pair ->
                val profile = pair.first
                val attempt = pair.second
                val name = profile?.name ?: "Buddy"
                val quitStart = attempt?.startEpochMillis
                
                if (attempt != null) {
                    nrtRepo.getUsage(attempt.id).map { usages ->
                        val nrtConsumed = usages.sumOf { it.quantity }
                        Triple(name, quitStart, nrtConsumed)
                    }
                } else {
                    flowOf(Triple(name, quitStart, 0))
                }
            }.collect { tuple ->
                buddyRepo.updateMyStats(myUid, tuple.first, tuple.second, tuple.third)
            }
        }
    }

    private fun observeMyProfile(myUid: String) {
        viewModelScope.launch {
            buddyRepo.observeMyProfile(myUid).collect { profile ->
                _uiState.update { it.copy(myProfile = profile, isLoading = false) }
                
                if (profile != null) {
                    observeBuddyProfiles(profile.buddyUids)
                    observePendingRequests(profile.pendingBuddyRequestUids)
                }
            }
        }
    }

    private fun observeBuddyProfiles(uids: List<String>) {
        buddiesJob?.cancel()
        if (uids.isEmpty()) {
            _uiState.update { it.copy(buddyProfiles = emptyList()) }
            return
        }
        buddiesJob = viewModelScope.launch {
            buddyRepo.observeBuddyProfiles(uids).collect { profiles ->
                _uiState.update { it.copy(buddyProfiles = profiles) }
            }
        }
    }
    
    private fun observePendingRequests(uids: List<String>) {
        requestsJob?.cancel()
        if (uids.isEmpty()) {
            _uiState.update { it.copy(pendingRequestProfiles = emptyList()) }
            return
        }
        requestsJob = viewModelScope.launch {
            buddyRepo.observeBuddyProfiles(uids).collect { profiles ->
                _uiState.update { it.copy(pendingRequestProfiles = profiles) }
            }
        }
    }

    fun sendBuddyRequest(code: String) {
        val myUid = _uiState.value.myProfile?.uid ?: return
        viewModelScope.launch {
            val success = buddyRepo.sendBuddyRequest(myUid, code)
            if (!success) {
                _uiState.update { it.copy(error = "Invalid pairing code") }
            }
        }
    }

    fun toggleSOS() {
        val myProfile = _uiState.value.myProfile ?: return
        viewModelScope.launch {
            buddyRepo.sendSOS(myProfile.uid, !myProfile.needsHelp)
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun acceptBuddyRequest(requesterUid: String) {
        val myProfile = _uiState.value.myProfile ?: return
        viewModelScope.launch {
            try {
                buddyRepo.acceptBuddyRequest(myProfile.uid, requesterUid)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun rejectBuddyRequest(requesterUid: String) {
        val myProfile = _uiState.value.myProfile ?: return
        viewModelScope.launch {
            try {
                buddyRepo.rejectBuddyRequest(myProfile.uid, requesterUid)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}