package com.unsmoke.app.feature.craving

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsmoke.app.core.designsystem.AppColors
import com.unsmoke.app.core.device.HapticGroundingManager
import kotlinx.coroutines.delay

@Composable
fun BreathingExercise(
    hapticManager: HapticGroundingManager,
    modifier: Modifier = Modifier
) {
    var phase by remember { mutableStateOf("Breathe In") }
    var scale by remember { mutableStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(
            durationMillis = when (phase) {
                "Breathe In" -> 4000
                "Hold" -> 7000
                "Breathe Out" -> 8000
                else -> 1000
            },
            easing = LinearEasing
        ),
        label = "BreathingScale"
    )

    LaunchedEffect(Unit) {
        while (true) {
            // Inhale (4s)
            phase = "Breathe In"
            scale = 1.5f
            hapticManager.playInhalePulse()
            delay(4000)

            // Hold (7s)
            phase = "Hold"
            scale = 1.5f
            hapticManager.playHoldPulse()
            delay(7000)

            // Exhale (8s)
            phase = "Breathe Out"
            scale = 0.8f
            hapticManager.playExhalePulse()
            delay(8000)
        }
    }

    Box(
        modifier = modifier.fillMaxWidth().height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .scale(animatedScale)
                .background(AppColors.Mint.copy(alpha = 0.3f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(animatedScale)
                .background(AppColors.Mint.copy(alpha = 0.6f), CircleShape)
        )
        Text(
            text = phase,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}