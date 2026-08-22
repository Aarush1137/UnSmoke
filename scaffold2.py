import os
import subprocess

def write_file(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content.strip() + '\n')

base_dir = r"E:\Projects\Unsmoke\app\src\main\kotlin\com\unsmoke\app"

files = {
    # Onboarding
    "feature/onboarding/OnboardingViewModel.kt": """
package com.unsmoke.app.feature.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _currentStep = MutableStateFlow(1)
    val currentStep = _currentStep.asStateFlow()
    
    fun nextStep() { _currentStep.value += 1 }
    fun previousStep() { if (_currentStep.value > 1) _currentStep.value -= 1 }
}
""",
    "feature/onboarding/OnboardingScreen.kt": """
package com.unsmoke.app.feature.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onComplete: () -> Unit
) {
    val step by viewModel.currentStep.collectAsStateWithLifecycle()
    
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Onboarding Step $step", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { if (step < 11) viewModel.nextStep() else onComplete() },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(if (step < 11) "NEXT" else "OPEN UNSMOKE")
            }
        }
    }
}
""",
    # Home Dashboard
    "feature/home/HomeViewModel.kt": """
package com.unsmoke.app.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
}

data class HomeUiState(
    val smokeFreeDays: Int = 3,
    val cigarettesAvoided: Int = 60,
    val moneySaved: Int = 600
)
""",
    "feature/home/HomeScreen.kt": """
package com.unsmoke.app.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCravingClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Good morning,", style = MaterialTheme.typography.bodyLarge)
            Text("Aarav 👋", style = MaterialTheme.typography.headlineLarge)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onCravingClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("I HAVE A CRAVING")
            }
        }
    }
}
""",
    # Craving Support
    "feature/craving/CravingViewModel.kt": """
package com.unsmoke.app.feature.craving

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CravingViewModel @Inject constructor() : ViewModel() {
}
""",
    "feature/craving/CravingScreen.kt": """
package com.unsmoke.app.feature.craving

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CravingScreen(
    viewModel: CravingViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Craving support", style = MaterialTheme.typography.headlineLarge)
        }
    }
}
""",
    # NRT Tracker
    "feature/nrt/NRTViewModel.kt": """
package com.unsmoke.app.feature.nrt

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NRTViewModel @Inject constructor() : ViewModel() {}
""",
    "feature/nrt/NRTScreen.kt": """
package com.unsmoke.app.feature.nrt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NRTScreen(
    viewModel: NRTViewModel = hiltViewModel()
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("NRT Tracker", style = MaterialTheme.typography.headlineLarge)
        }
    }
}
"""
}

# Write files
for path, content in files.items():
    full_path = os.path.join(base_dir, path.replace("/", os.sep))
    write_file(full_path, content)

print("Scaffolded all files.")
