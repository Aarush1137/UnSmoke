package com.unsmoke.app.core.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unsmoke.app.core.designsystem.*

@Composable
fun BreathingOrb(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    isCravingMode: Boolean = false
) {
    val darkTheme = isSystemInDarkTheme() || isCravingMode
    
    val color = if (darkTheme) DarkPrimary else LightPrimary
    val glowColor = if (darkTheme) DarkPrimaryContainer else LightPrimaryContainer

    val infiniteTransition = rememberInfiniteTransition(label = "BreathingOrb")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbScale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbAlpha"
    )

    Canvas(modifier = modifier.size(size)) {
        // Outer Glow
        drawCircle(
            color = glowColor.copy(alpha = alpha * 0.5f),
            radius = (size.toPx() / 2) * scale * 1.2f
        )
        // Inner Core
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = (size.toPx() / 2) * scale
        )
    }
}
