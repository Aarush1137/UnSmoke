package com.unsmoke.app.feature.plan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlanScreen(onBack: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("When I crave, I will...", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))
            val plan = listOf("Breathe deeply", "Wait 10 minutes", "Drink water", "Use NRT if needed", "Go for a walk", "Call someone")
            plan.forEachIndexed { index, item ->
                Text("${index + 1}. $item", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("EDIT PLAN")
            }
        }
    }
}
