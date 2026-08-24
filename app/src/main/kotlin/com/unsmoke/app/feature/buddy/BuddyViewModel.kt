package com.unsmoke.app.feature.buddy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.domain.repository.BuddyProfile
import com.unsmoke.app.core.domain.repository.BuddyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BuddyUiState(
    val myProfile: BuddyProfile? = null,
    val buddyProfile: BuddyProfile? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class BuddyViewModel @Inject constructor(
    private val buddyRepo: BuddyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BuddyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val myUid = buddyRepo.signInAnonymously()
                observeProfiles(myUid)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun observeProfiles(myUid: String) {
        viewModelScope.launch {
            buddyRepo.observeMyProfile(myUid).collect { profile ->
                _uiState.update { it.copy(myProfile = profile, isLoading = false) }
                
                // If we have a buddy, observe them too
                profile?.buddyUid?.let { buddyUid ->
                    observeBuddy(buddyUid)
                }
            }
        }
    }

    private fun observeBuddy(buddyUid: String) {
        viewModelScope.launch {
            buddyRepo.observeBuddyProfile(buddyUid).collect { profile ->
                _uiState.update { it.copy(buddyProfile = profile) }
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
    fun acceptBuddyRequest() {
        val myProfile = _uiState.value.myProfile ?: return
        val pendingUid = myProfile.pendingBuddyRequestUid ?: return
        viewModelScope.launch {
            try {
                buddyRepo.acceptBuddyRequest(myProfile.uid, pendingUid)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun rejectBuddyRequest() {
        val myProfile = _uiState.value.myProfile ?: return
        viewModelScope.launch {
            try {
                buddyRepo.rejectBuddyRequest(myProfile.uid)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}