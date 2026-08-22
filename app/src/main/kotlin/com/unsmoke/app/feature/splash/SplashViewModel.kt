package com.unsmoke.app.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/** Exposes the persisted onboarding state while the launch animation is shown. */
@HiltViewModel
class SplashViewModel @Inject constructor(
    preferences: UserPreferencesDataStore
) : ViewModel() {
    val onboardingComplete: StateFlow<Boolean> = preferences.onboardingComplete.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
}
