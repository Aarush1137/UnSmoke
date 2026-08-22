package com.unsmoke.app.feature.craving

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CravingTimerScreen(
    onDefeated: () -> Unit,
    onSmoked: () -> Unit,
    viewModel: CravingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var timeLeft by remember { mutableStateOf(600) } // 10 minutes

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

    LaunchedEffect(state.step) {
        if (state.step == CravingStep.OUTCOME) onDefeated()
        if (state.step == CravingStep.LAPSE) onSmoked()
    }

    val darkBg = Color(0xFF0B1211)
    val primaryTeal = Color(0xFF55D8C6)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride it out", color = Color.White) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Rounded.ArrowBack, tint = Color.White, contentDescription = null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBg)
            )
        },
        containerColor = darkBg
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.size(280.dp), contentAlignment = Alignment.Center) {
                GlowingRing(primaryTeal)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Breathe in", color = primaryTeal, fontSize = 16.sp)
                    val mins = timeLeft / 60
                    val secs = timeLeft % 60
                    Text(String.format("%02d:%02d", mins, secs), color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Bold)
                    Text("remaining", color = Color.LightGray, fontSize = 14.sp)
                }
            }
            
            Spacer(Modifier.height(48.dp))
            Text("Cravings come in waves.", color = Color.White, fontSize = 18.sp)
            Text("They peak, then they pass.", color = Color.Gray, fontSize = 16.sp)
            
            Spacer(Modifier.height(64.dp))
            Button(
                onClick = { viewModel.resolveCraving("DEFEATED", context) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("I BEAT THIS CRAVING", color = darkBg, fontWeight = FontWeight.Bold)
            }
            
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = { viewModel.resolveCraving("SMOKED", context) }) {
                Text("I SMOKED", color = Color.Gray)
            }
        }
    }
}

@Composable
fun GlowingRing(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(animation = tween(2000, easing = EaseInOutSine), repeatMode = RepeatMode.Reverse),
        label = "scale"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2.5f
        
        drawCircle(color = color.copy(alpha = 0.15f), radius = radius * 1.3f * scale, center = center)
        drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 1.15f * scale, center = center)
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 6.dp.toPx()))
    }
}
