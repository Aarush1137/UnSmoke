package com.unsmoke.app.feature.craving

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CravingScreen(
    onTimerStart: () -> Unit,
    onBack: () -> Unit,
    viewModel: CravingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.step) {
        if (state.step == CravingStep.TIMER) {
            onTimerStart()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Craving support") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Rounded.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.step) {
                CravingStep.INTENSITY -> IntensityStep(state.intensity, viewModel::updateIntensity, viewModel::proceedToTrigger)
                CravingStep.TRIGGER -> TriggerStep(state.selectedTriggers, viewModel::toggleTrigger, viewModel::proceedToNeed)
                CravingStep.NEED -> NeedStep(viewModel::startTimer)
                else -> Unit
            }
        }
    }
}

@Composable
private fun IntensityStep(intensity: Int, onChange: (Int) -> Unit, onNext: () -> Unit) {
    Text("How strong is your craving?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(48.dp))
    Text(intensity.toString(), fontSize = 72.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Text("/10", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(Modifier.height(48.dp))
    Slider(
        value = intensity.toFloat(),
        onValueChange = { onChange(it.toInt()) },
        valueRange = 1f..10f,
        steps = 8
    )
    Spacer(Modifier.weight(1f))
    Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("NEXT") }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TriggerStep(selected: Set<String>, onToggle: (String) -> Unit, onNext: () -> Unit) {
    val triggers = listOf("Stress", "After food", "Coffee", "Boredom", "Someone smoking", "Loneliness", "Habit", "Other")
    Text("What triggered it?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(32.dp))
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        triggers.forEach { trigger ->
            FilterChip(
                selected = selected.contains(trigger),
                onClick = { onToggle(trigger) },
                label = { Text(trigger) },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
            )
        }
    }
    Spacer(Modifier.weight(1f))
    Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("NEXT") }
}

@Composable
private fun NeedStep(onStart: () -> Unit) {
    Spacer(Modifier.height(64.dp))
    Text("Let's protect the next 10 minutes.", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    Spacer(Modifier.height(32.dp))
    Button(
        onClick = onStart,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text("START 10-MINUTE RESET", fontWeight = FontWeight.Bold)
    }
}
