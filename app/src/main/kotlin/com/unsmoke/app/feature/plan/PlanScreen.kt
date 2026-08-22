package com.unsmoke.app.feature.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Plan", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF011113),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF011113) // Dark theme background
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(16.dp))
            Text("When I crave, I will...", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(32.dp))
            
            val plan = listOf(
                "Breathe deeply" to Icons.Rounded.Air,
                "Wait 10 minutes" to Icons.Rounded.Timer,
                "Drink water" to Icons.Rounded.LocalDrink,
                "Use NRT if needed" to Icons.Rounded.MedicalServices,
                "Go for a walk" to Icons.Rounded.DirectionsWalk,
                "Call someone" to Icons.Rounded.Phone,
                "Remind myself why I quit" to Icons.Rounded.FormatQuote
            )
            
            plan.forEach { (item, icon) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = Color(0xFFB6C4BF), modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(16.dp))
                    Text(item, fontSize = 18.sp, color = Color(0xFFECF5F1), fontWeight = FontWeight.Medium)
                }
                HorizontalDivider(color = Color(0xFF263A37))
            }
            
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {}, 
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B856E))
            ) {
                Text("EDIT PLAN", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
