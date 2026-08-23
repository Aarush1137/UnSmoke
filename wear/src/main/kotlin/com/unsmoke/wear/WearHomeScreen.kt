package com.unsmoke.wear

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn

@Composable
fun WearHomeScreen(startEpoch: Long?, onBreatheClick: () -> Unit, modifier: Modifier = Modifier) {
    ScalingLazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 28.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "UnSmoke",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp),
                color = androidx.compose.ui.graphics.Color(0xFF26C6DA)
            )
        }
        
        item {
            if (startEpoch != null && startEpoch > 0) {
                WearLiveTimer(startEpoch = startEpoch)
            } else {
                Text(
                    text = "Syncing with phone...",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }
        
        item {
            Button(
                onClick = { /* TODO: Sync SOS to Mobile */ },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                colors = androidx.wear.compose.material.ButtonDefaults.primaryButtonColors(backgroundColor = androidx.compose.ui.graphics.Color(0xFFE57373))
            ) {
                Text("SEND SOS", fontWeight = FontWeight.Bold)
            }
        }
        
        item {
            Button(
                onClick = onBreatheClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                colors = androidx.wear.compose.material.ButtonDefaults.secondaryButtonColors()
            ) {
                Text("Breathe")
            }
        }
    }
}

@Composable
fun WearLiveTimer(startEpoch: Long) {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }
    
    val diff = kotlin.math.max(0L, currentTime - startEpoch)
    val totalSeconds = diff / 1000
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = "d h m", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Smoke Free", fontSize = 12.sp, color = androidx.compose.ui.graphics.Color.LightGray)
    }
}