package com.unsmoke.app.feature.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AchievementsScreen(onBack: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Achievements", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))
            Card(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                    Text("One Week", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("7 days smoke-free")
                }
            }
            Spacer(Modifier.height(32.dp))
            Text("Your badges", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                BadgeCard("1")
                BadgeCard("7")
                BadgeCard("30")
                BadgeCard("90")
            }
        }
    }
}

@Composable
fun BadgeCard(label: String) {
    Card(modifier = Modifier.size(64.dp)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
