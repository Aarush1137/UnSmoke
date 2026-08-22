package com.unsmoke.app.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onPlanClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Profile", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))
            Text("Aarav", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Smoke-free since Aug 2026", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(48.dp))
            Button(onClick = onPlanClick, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("My Quit Plan") }
            Spacer(Modifier.height(16.dp))
            Button(onClick = onAchievementsClick, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("Achievements") }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(onClick = onSettingsClick, modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("Settings") }
        }
    }
}
