package com.unsmoke.app.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(
    progress: Float, // 0f to 1f
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable () -> Unit = {}
) {
    var animationPlayed by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = animationPlayed,
        animationSpec = tween(durationMillis = 1000),
        label = "ProgressAnimation"
    )

    LaunchedEffect(progress) {
        animationPlayed = progress
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            // Background arc
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Foreground arc
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * animatedProgress,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        content()
    }
}
