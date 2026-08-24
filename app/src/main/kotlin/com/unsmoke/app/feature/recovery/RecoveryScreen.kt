package com.unsmoke.app.feature.recovery

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.UnSmokeColors
import com.unsmoke.app.core.designsystem.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryScreen(
    onComplete: () -> Unit,
    onBack: () -> Unit,
    viewModel: RecoveryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) onComplete()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recovery", color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Rounded.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            when (state.step) {
                1 -> {
                    Text("A slip is not a failure.", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("It's a bump, not the end. Let's understand what happened so you can get back stronger.", color = Color.White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text("How many did you smoke?", color = MaterialTheme.colorScheme.primary)
                    Slider(
                        value = state.cigarettesSmoked.toFloat(),
                        onValueChange = { viewModel.updateCigarettes(it.toInt()) },
                        valueRange = 1f..20f,
                        steps = 19
                    )
                    Text("${state.cigarettesSmoked} cigarettes", color = Color.White)
                    
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { viewModel.nextStep() },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("CONTINUE", color = Color.White) }
                }
                2 -> {
                    Text("What triggered this?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(24.dp))
                    val triggers = listOf("Stress", "Social", "Boredom", "Alcohol", "Habit", "Other")
                    triggers.forEach { trigger ->
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            RadioButton(
                                selected = state.trigger == trigger,
                                onClick = { viewModel.updateTrigger(trigger) },
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(trigger, color = Color.White, modifier = Modifier.padding(top = 12.dp))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { viewModel.nextStep() },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("CONTINUE", color = Color.White) }
                }
                3 -> {
                    Text("Moving Forward", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Every day is a new chance. Your historical progress is saved.", color = Color.White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = { viewModel.finishRecovery(resetStreak = false) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Surface)
                    ) { Text("IT WAS JUST A LAPSE. KEEP MY STREAK.", color = MaterialTheme.colorScheme.primary) }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { viewModel.finishRecovery(resetStreak = true) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("START A NEW QUIT ATTEMPT", color = Color.White) }
                }
            }
        }
    }
}


