package com.unsmoke.app.core.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unsmoke.app.core.designsystem.unSmokeColors

@Composable
fun BreathingOrb(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    isActive: Boolean = true,
    alpha: Float = 1f,
    showInstruction: Boolean = false, // show "Breathe in" / "Breathe out"
    onCycleComplete: (() -> Unit)? = null
) {
    val colors = MaterialTheme.unSmokeColors
    
    // Breathing cycle: inhale 4s, hold 1s, exhale 6s, pause 1s = 12s total
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 12000
                0.75f at 0 using EaseInOut     // start small
                1.0f at 4000 using EaseInOut   // inhale 4s ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ full
                1.0f at 5000 using LinearEasing // hold 1s
                0.75f at 11000 using EaseInOut  // exhale 6s ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ small  
                0.75f at 12000 using LinearEasing // pause 1s
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "orbScale"
    )
    
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 12000
                0.3f at 0 using EaseInOut
                0.8f at 4000 using EaseInOut
                0.8f at 5000 using LinearEasing
                0.3f at 11000 using EaseInOut
                0.3f at 12000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "orbGlow"
    )
    
    // Instruction text cycles
    val breathPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 12000f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )
    val instruction = when {
        breathPhase < 4000 -> "Breathe in"
        breathPhase < 5000 -> "Hold"
        breathPhase < 11000 -> "Breathe out"
        else -> ""
    }
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showInstruction) {
            AnimatedContent(targetState = instruction, label = "instruction") { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.orbCore.copy(alpha = 0.9f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            // Outer glow
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = colors.orbGlow,
                    radius = (size.toPx() / 2) * scale * 1.4f,
                    alpha = glowAlpha * 0.4f * alpha
                )
                drawCircle(
                    color = colors.orbGlow,
                    radius = (size.toPx() / 2) * scale * 1.2f,
                    alpha = glowAlpha * 0.3f * alpha
                )
                // Main orb
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.orbCore.copy(alpha = alpha),
                            colors.orbCore.copy(alpha = alpha * 0.6f),
                            colors.orbGlow.copy(alpha = alpha * 0.3f)
                        ),
                        radius = (size.toPx() / 2) * scale
                    ),
                    radius = (size.toPx() / 2) * scale
                )
            }
        }
    }
}
