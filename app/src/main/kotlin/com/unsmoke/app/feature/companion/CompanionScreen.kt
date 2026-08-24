package com.unsmoke.app.feature.companion

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanionScreen(
    onBack: () -> Unit,
    viewModel: CompanionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val companion = state.companion

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Virtual Companion") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            } else if (companion != null) {
                // Name & Health
                Text(companion.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Favorite, contentDescription = "Health", tint = Color.Red, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(8.dp))
                    LinearProgressIndicator(
                        progress = { companion.health / 100f },
                        modifier = Modifier.width(200.dp).height(12.dp),
                        color = Color.Red,
                        trackColor = Color.DarkGray
                    )
                }
                
                Spacer(Modifier.height(48.dp))

                // Render the cute alien
                VirtualPetCanvas(health = companion.health, stage = companion.stage)

                Spacer(Modifier.height(48.dp))

                // Actions
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { viewModel.feedCompanion() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Fastfood, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Feed / Heal")
                    }
                }
                
                Spacer(Modifier.height(32.dp))
                Text(
                    "Heals passively as you maintain your quit streak!\nIf you relapse,  takes damage.",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun VirtualPetCanvas(health: Int, stage: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color = when {
        health > 70 -> Color(0xFF00E676) // Healthy Green
        health > 30 -> Color(0xFFFFEA00) // Yellow
        else -> Color(0xFFFF1744) // Danger Red
    }

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().offset(y = bounce.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            
            // Draw a cute blob body
            drawCircle(
                color = color,
                radius = 70f + (stage * 10f),
                center = center
            )
            
            // Eyes
            drawCircle(
                color = Color.Black,
                radius = 8f,
                center = Offset(center.x - 25f, center.y - 15f)
            )
            drawCircle(
                color = Color.Black,
                radius = 8f,
                center = Offset(center.x + 25f, center.y - 15f)
            )
            
            // Mouth based on health
            val mouthPath = Path()
            if (health > 50) {
                // Smile
                mouthPath.moveTo(center.x - 20f, center.y + 10f)
                mouthPath.quadraticBezierTo(
                    center.x, center.y + 30f,
                    center.x + 20f, center.y + 10f
                )
            } else {
                // Sad
                mouthPath.moveTo(center.x - 20f, center.y + 20f)
                mouthPath.quadraticBezierTo(
                    center.x, center.y,
                    center.x + 20f, center.y + 20f
                )
            }
            drawPath(mouthPath, color = Color.Black, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6f))
            }
    }
}