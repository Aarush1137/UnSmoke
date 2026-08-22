package com.unsmoke.app.feature.checkin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors

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
                title = { Text("Daily Check-in", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onComplete) {
                        Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("How was your day?", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(32.dp))
            
            // Mood Selector
            Text("OVERALL MOOD", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val moods = listOf("Awful", "Bad", "Okay", "Good", "Great")
                moods.forEachIndexed { index, moodLabel ->
                    val moodValue = index + 1
                    val isSelected = state.mood == moodValue
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) AppColors.Mint else Color(0xFF1E2625))
                                .clickable { viewModel.updateMood(moodValue) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = moodValue.toString(),
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(moodLabel, color = if (isSelected) AppColors.Mint else Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            
            Spacer(Modifier.height(40.dp))
            Text("SLEEP QUALITY", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Slider(
                value = state.sleepQuality.toFloat(),
                onValueChange = { viewModel.updateSleep(it.toInt()) },
                valueRange = 1f..5f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = AppColors.Teal,
                    activeTrackColor = AppColors.Teal,
                    inactiveTrackColor = Color.DarkGray
                )
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Terrible", color = Color.Gray, fontSize = 12.sp)
                Text("Excellent", color = Color.Gray, fontSize = 12.sp)
            }
            
            Spacer(Modifier.height(40.dp))
            Text("STRESS LEVEL", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Slider(
                value = state.stressLevel.toFloat(),
                onValueChange = { viewModel.updateStress(it.toInt()) },
                valueRange = 1f..5f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = AppColors.Amber,
                    activeTrackColor = AppColors.Amber,
                    inactiveTrackColor = Color.DarkGray
                )
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Low", color = Color.Gray, fontSize = 12.sp)
                Text("High", color = Color.Gray, fontSize = 12.sp)
            }
            
            Spacer(Modifier.height(40.dp))
            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.updateNotes(it) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                placeholder = { Text("Any thoughts on today? Triggers you noticed?", color = Color.Gray) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.DarkGray,
                    focusedBorderColor = AppColors.Mint,
                    unfocusedContainerColor = Color(0xFF1E2625),
                    focusedContainerColor = Color(0xFF1E2625),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(Modifier.weight(1f))
            Button(
                onClick = { viewModel.submitCheckIn(onComplete) },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Mint),
                shape = RoundedCornerShape(16.dp),
                enabled = state.mood > 0
            ) {
                Text("SAVE JOURNAL", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


