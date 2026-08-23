package com.unsmoke.app.feature.craving

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors
import kotlinx.coroutines.delay
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CravingTimerScreen(
    onDefeated: () -> Unit,
    onSmoked: () -> Unit,
    viewModel: CravingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val targetEndTime by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(System.currentTimeMillis() + 600_000L) }
    var timeLeft by remember { mutableStateOf(600) }

    LaunchedEffect(targetEndTime) {
        while (true) {
            val remaining = ((targetEndTime - System.currentTimeMillis()) / 1000).toInt()
            if (remaining <= 0) {
                timeLeft = 0
                break
            }
            timeLeft = remaining
            delay(1000)
        }
    }

    LaunchedEffect(state.step) {
        if (state.step == CravingStep.OUTCOME) onDefeated()
        if (state.step == CravingStep.LAPSE) onSmoked()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride it out", color = Color.White) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, tint = Color.White, contentDescription = null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            
            // Urge Surfing Visualizer
            Box(modifier = Modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
                UrgeSurfingWave(timeLeft)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val mins = timeLeft / 60
                    val secs = timeLeft % 60
                    Text(String.format("%02d:%02d", mins, secs), color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Bold)
                    Text("remaining", color = Color.LightGray, fontSize = 16.sp)
                }
            }
            
            Spacer(Modifier.height(32.dp))
            Text("Cravings peak at 3 minutes, then fade.", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text("You are surfing the urge.", color = AppColors.Mint, fontSize = 16.sp)
            
            Spacer(Modifier.height(32.dp))
            
            // The 4 D's Toolkit
            Text("The 4 D's Toolkit", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ToolkitItem(Icons.Rounded.Timer, "Delay", AppColors.Teal)
                ToolkitItem(Icons.Rounded.Air, "Breathe", AppColors.Mint)
                ToolkitItem(Icons.Rounded.LocalDrink, "Drink", AppColors.Amber)
                ToolkitItem(Icons.AutoMirrored.Rounded.DirectionsRun, "Distract", AppColors.Teal)
            }
            
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { viewModel.resolveCraving("DEFEATED") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Mint)
            ) {
                Text("I BEAT THIS CRAVING", color = AppColors.Background, fontWeight = FontWeight.Bold)
            }
            
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = { viewModel.resolveCraving("SMOKED") }) {
                Text("I SMOKED", color = Color.Gray)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ToolkitItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AppColors.Surface,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(32.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(label, color = Color.LightGray, fontSize = 12.sp)
    }
}

@Composable
fun UrgeSurfingWave(timeLeft: Int) {
    // A craving lasts 10 mins (600s). Peaks between 90s (510) and 180s (420).
    // We map elapsed time (600 - timeLeft) to an intensity multiplier.
    val elapsed = 600 - timeLeft
    val intensity = when {
        elapsed < 90 -> elapsed / 90f // Ramp up
        elapsed in 90..180 -> 1f // Peak
        else -> 1f - ((elapsed - 180f) / 420f).coerceIn(0f, 1f) // Fade out
    }
    
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(2000, easing = LinearEasing), repeatMode = RepeatMode.Restart)
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val centerY = height / 2f
        val baseAmplitude = height * 0.15f
        val currentAmplitude = baseAmplitude + (baseAmplitude * 1.5f * intensity)
        
        val path = Path()
        path.moveTo(0f, centerY)
        for (x in 0..width.toInt() step 5) {
            // Wavelength
            val frequency = 2f * Math.PI.toFloat() / width
            val y = centerY + sin((x * frequency) + phase) * currentAmplitude
            path.lineTo(x.toFloat(), y)
        }
        
        val waveColor = androidx.compose.ui.graphics.lerp(AppColors.Teal, AppColors.Error, intensity * 0.5f)
        
        drawPath(
            path = path,
            color = waveColor.copy(alpha = 0.5f + (0.5f * intensity)),
            style = Stroke(width = 4.dp.toPx())
        )
    }
}
