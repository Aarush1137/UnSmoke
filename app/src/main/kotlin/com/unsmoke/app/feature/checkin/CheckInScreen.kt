package com.unsmoke.app.feature.checkin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
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
fun CheckInScreen(
    onComplete: () -> Unit,
    viewModel: CheckInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Check-in") },
                navigationIcon = {
                    IconButton(onClick = onComplete) {
                        Icon(Icons.Rounded.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("How are you feeling?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            
            // Mood Selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf(1, 2, 3, 4, 5).forEach { moodValue ->
                    FilterChip(
                        selected = state.mood == moodValue,
                        onClick = { viewModel.updateMood(moodValue) },
                        label = { Text(moodValue.toString()) }
                    )
                }
            }
            
            Spacer(Modifier.height(32.dp))
            Text("Sleep Quality", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Slider(
                value = state.sleepQuality.toFloat(),
                onValueChange = { viewModel.updateSleep(it.toInt()) },
                valueRange = 1f..5f,
                steps = 3
            )
            
            Spacer(Modifier.height(32.dp))
            Text("Stress Level", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Slider(
                value = state.stressLevel.toFloat(),
                onValueChange = { viewModel.updateStress(it.toInt()) },
                valueRange = 1f..5f,
                steps = 3
            )
            
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.updateNotes(it) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                label = { Text("Notes for today") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.weight(1f))
            Button(
                onClick = { viewModel.submitCheckIn(onComplete) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B856E))
            ) {
                Text("SAVE CHECK-IN", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
