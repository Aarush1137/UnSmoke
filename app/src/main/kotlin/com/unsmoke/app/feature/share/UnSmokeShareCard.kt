package com.unsmoke.app.feature.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsmoke.app.core.designsystem.UnSmokeColors
import com.unsmoke.app.core.designsystem.AppColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun UnSmokeShareCard(
    userName: String,
    smokeFreeDays: Int,
    cigarettesAvoided: Double,
    moneySaved: Double,
    tagline: String,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.background)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.8f) // Social media portrait ratio roughly
            .background(gradient, RoundedCornerShape(24.dp))
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("UnSmoke", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            // Main Stat
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "PROUD OF",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "$smokeFreeDays DAYS",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "SMOKE-FREE!",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Sub Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$cigarettesAvoided", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = "Cigarettes Avoided", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$moneySaved", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(text = "Saved", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            }

            // Footer
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(tagline, color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Shared by $userName on UnSmoke",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp
                )
            }
        }
    }
}



