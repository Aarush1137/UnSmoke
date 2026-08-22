package com.unsmoke.app.core.designsystem.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(
    progress: Float, // 0f to 1f
    modifier: Modifier = Modifier,
    size: Dp = 220.dp,
    strokeWidth: Dp = 12.dp,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    startAngle: Float = -90f,
    sweepAngle: Float = 360f // leaves a gap at bottom
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "ringProgress"
    )
    
    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val diameter = size.toPx() - strokeWidthPx
        val offset = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
        val arcSize = Size(diameter, diameter)
        
        // Track
        drawArc(
            color = trackColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = offset,
            size = arcSize,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
        // Progress  
        drawArc(
            color = progressColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle * animatedProgress,
            useCenter = false,
            topLeft = offset,
            size = arcSize,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
    }
}
