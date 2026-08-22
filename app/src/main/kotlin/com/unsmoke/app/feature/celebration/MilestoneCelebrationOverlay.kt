package com.unsmoke.app.feature.celebration

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsmoke.app.core.designsystem.UnSmokeColors
import kotlinx.coroutines.delay

@Composable
fun MilestoneCelebrationOverlay(
    milestoneDays: Int,
    onDismiss: () -> Unit,
    onShare: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(0.5f) }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(100)
        scale = 1.0f
    }

    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Animated Badge
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(UnSmokeColors.Mint.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = UnSmokeColors.Amber
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "\ DAYS",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "SMOKE-FREE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = UnSmokeColors.Mint,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "A massive achievement. You are protecting your health and your future.",
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = onShare,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UnSmokeColors.Teal),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Rounded.Share, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SHARE MILESTONE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onDismiss) {
                    Text("Continue Journey", color = Color.White.copy(alpha = 0.6f))
                }
            }
        }
    }
}
