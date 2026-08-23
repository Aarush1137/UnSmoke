package com.unsmoke.app.feature.craving

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
                title = { Text("Log a Craving", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Background,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
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
private fun ColumnScope.IntensityStep(intensity: Int, onChange: (Int) -> Unit, onNext: () -> Unit) {
    Text("How strong is your craving right now?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
    Spacer(Modifier.height(48.dp))
    
    // Intensity color dynamically changes from Green to Red
    val intensityColor = when (intensity) {
        in 1..3 -> AppColors.Mint
        in 4..7 -> AppColors.Amber
        else -> Color(0xFFFF5252)
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(intensityColor.copy(alpha = 0.1f), CircleShape)
            .border(4.dp, intensityColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(intensity.toString(), fontSize = 80.sp, fontWeight = FontWeight.ExtraBold, color = intensityColor)
            Text("OUT OF 10", fontSize = 14.sp, color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }
    }

    Spacer(Modifier.height(64.dp))

    Slider(
        value = intensity.toFloat(),
        onValueChange = { onChange(it.toInt()) },
        valueRange = 1f..10f,
        steps = 8,
        colors = SliderDefaults.colors(
            thumbColor = intensityColor,
            activeTrackColor = intensityColor,
            inactiveTrackColor = Color.DarkGray
        ),
        modifier = Modifier.fillMaxWidth(0.9f)
    )

    Spacer(Modifier.weight(1f))
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth().height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Teal),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text("NEXT", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.TriggerStep(selected: Set<String>, onToggle: (String) -> Unit, onNext: () -> Unit) {
    val triggers = listOf("Stress", "After food", "Coffee", "Boredom", "Socializing", "Drinking", "Waking up", "Just habit")
    
    Text("What triggered it?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Spacer(Modifier.height(16.dp))
    Text("Select all that apply", fontSize = 14.sp, color = Color.White.copy(alpha = 0.6f))
    Spacer(Modifier.height(32.dp))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        triggers.forEach { trigger ->
            val isSelected = selected.contains(trigger)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (isSelected) AppColors.Mint else Color(0xFF1E2625))
                    .clickable { onToggle(trigger) }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = trigger,
                    color = if (isSelected) Color.Black else Color.White.copy(alpha = 0.8f),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }
    }

    Spacer(Modifier.weight(1f))
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth().height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Teal),
        shape = RoundedCornerShape(16.dp),
        enabled = selected.isNotEmpty()
    ) {
        Text("CONTINUE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColumnScope.NeedStep(onNext: () -> Unit) {
    Text("Protect the next 10 minutes.", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = AppColors.Mint, textAlign = TextAlign.Center)
    Spacer(Modifier.height(16.dp))
    Text("Cravings only last a few minutes. If you can beat the clock, you win.", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f), textAlign = TextAlign.Center)
    
    Spacer(Modifier.weight(1f))
    Button(
        onClick = onNext,
        modifier = Modifier.fillMaxWidth().height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text("START RECOVERY TIMER", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

