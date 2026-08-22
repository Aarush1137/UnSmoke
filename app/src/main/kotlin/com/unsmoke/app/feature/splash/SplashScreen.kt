package com.unsmoke.app.feature.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: (onboardingComplete: Boolean) -> Unit,
    isDark: Boolean = true,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val onboardingComplete by viewModel.onboardingComplete.collectAsStateWithLifecycle()
    val alphaAnim = remember { Animatable(0f) }
    val offsetYAnim = remember { Animatable(20f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, tween(1000, easing = LinearOutSlowInEasing))
        offsetYAnim.animateTo(0f, tween(1000, easing = LinearOutSlowInEasing))
        delay(1500)
        onTimeout(onboardingComplete)
    }

    val bgColor = if (isDark) Color(0xFF011113) else Color(0xFFFAFAF8)
    val textColor = if (isDark) Color.White else Color(0xFF18201E)
    val logoColor = if (isDark) Color(0xFF8FDCD0) else Color(0xFF0B856E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        // Draw the background wave
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val wavePath = Path().apply {
                moveTo(0f, height * 0.75f)
                cubicTo(
                    width * 0.25f, height * 0.7f,
                    width * 0.75f, height * 0.8f,
                    width, height * 0.75f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                path = wavePath,
                color = logoColor.copy(alpha = if (isDark) 0.1f else 0.05f)
            )
            
            val wavePath2 = Path().apply {
                moveTo(0f, height * 0.8f)
                cubicTo(
                    width * 0.3f, height * 0.85f,
                    width * 0.7f, height * 0.75f,
                    width, height * 0.8f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                path = wavePath2,
                color = logoColor.copy(alpha = if (isDark) 0.15f else 0.08f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset(y = offsetYAnim.value.dp)
                .fillMaxWidth()
        ) {
            // Draw abstract 'U' logo
            Canvas(modifier = Modifier.size(100.dp)) {
                val s = size.width
                val strokeW = s * 0.2f
                // The U shape
                val uPath = Path().apply {
                    moveTo(s * 0.2f, s * 0.1f)
                    lineTo(s * 0.2f, s * 0.6f)
                    cubicTo(
                        s * 0.2f, s * 0.9f,
                        s * 0.8f, s * 0.9f,
                        s * 0.8f, s * 0.6f
                    )
                    lineTo(s * 0.8f, s * 0.3f)
                }
                drawPath(
                    path = uPath,
                    color = logoColor,
                    style = Stroke(
                        width = strokeW,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    ),
                    alpha = alphaAnim.value
                )
                
                // The mint leaf/wave abstract part 
                val leafPath = Path().apply {
                    moveTo(s * 0.8f, s * 0.4f)
                    cubicTo(
                        s * 1.0f, s * 0.4f,
                        s * 1.1f, s * 0.1f,
                        s * 0.9f, s * 0.1f
                    )
                    cubicTo(
                        s * 0.7f, s * 0.1f,
                        s * 0.8f, s * 0.4f,
                        s * 0.8f, s * 0.4f
                    )
                }
                drawPath(
                    path = leafPath,
                    color = Color(0xFF8FDCD0), // Mint color
                    alpha = alphaAnim.value
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "UnSmoke",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                
                color = textColor.copy(alpha = alphaAnim.value)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "One craving at a time.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor.copy(alpha = alphaAnim.value * 0.7f)
            )
        }
    }
}
