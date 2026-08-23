package com.unsmoke.wear

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun WearCravingScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var phase by remember { mutableStateOf("Ready") }
    var scale by remember { mutableFloatStateOf(1f) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "breathe")
    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(animatedScale) {
        if (animatedScale > 1.15f && phase != "Hold") {
            phase = "Hold"
            pulseWear(context)
        } else if (animatedScale < 0.85f && phase != "Inhale") {
            phase = "Inhale"
            pulseWear(context)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCircle(
                color = Color(0xFF26C6DA),
                radius = size.minDimension / 2 * animatedScale,
                style = Stroke(width = 8f)
            )
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = phase, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Text("X", fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun pulseWear(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vm.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    
    if (vibrator.hasVibrator()) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}