package com.unsmoke.app.feature.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insights", fontWeight = FontWeight.SemiBold) },
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(Modifier.height(16.dp)) }
            
            item {
                InsightCard(
                    title = "TOP TRIGGER",
                    value = "Stress",
                    subtitle = "42% of your cravings",
                    icon = Icons.Rounded.Psychology,
                    iconTint = Color(0xFFE77979)
                )
            }
            
            item {
                InsightCard(
                    title = "HIGH RISK TIME",
                    value = "7 PM ? 9 PM",
                    subtitle = "Most cravings happen during this period.",
                    icon = Icons.Rounded.Schedule,
                    iconTint = Color(0xFFD8AC60)
                )
            }
            
            item {
                InsightCard(
                    title = "BEST COPING TOOL",
                    value = "10-minute delay",
                    subtitle = "Works best for you.",
                    icon = Icons.Rounded.Shield,
                    iconTint = Color(0xFF4EC9A6)
                )
            }
            
            item {
                InsightCard(
                    title = "YOU'RE IMPROVING",
                    value = "Intensity reduced",
                    subtitle = "Craving intensity has dropped by 15%.",
                    icon = Icons.Rounded.TrendingDown,
                    iconTint = Color(0xFF55D8C6)
                )
            }
            
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun InsightCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2829))
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF0A2022), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, fontSize = 12.sp, color = Color(0xFF82918B), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(4.dp))
                Text(value, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(subtitle, fontSize = 14.sp, color = Color(0xFFB6C4BF))
            }
        }
    }
}
