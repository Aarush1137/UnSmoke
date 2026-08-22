package com.unsmoke.app.feature.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.components.ProgressRing

@Composable
fun ProgressScreen(
    onInsightsClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFFAFAF8)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(16.dp))
            Text("Progress", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF18201E))
            Spacer(Modifier.height(24.dp))

            // Tab selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                FilterChip(selected = state.timeFilter == "7 Days", onClick = { viewModel.setTimeFilter("7 Days") }, label = { Text("7 Days") })
                FilterChip(selected = state.timeFilter == "30 Days", onClick = { viewModel.setTimeFilter("30 Days") }, label = { Text("30 Days") })
                FilterChip(selected = state.timeFilter == "3 Months", onClick = { viewModel.setTimeFilter("3 Months") }, label = { Text("3 Months") })
                FilterChip(selected = state.timeFilter == "1 Year", onClick = { viewModel.setTimeFilter("1 Year") }, label = { Text("1 Year") })
            }

            Spacer(Modifier.height(32.dp))
            
            // Hero Ring
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ProgressRing(progress = 0.75f, size = 180.dp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B856E))
                    Text("Days Free", fontSize = 16.sp, color = Color(0xFF596560))
                }
            }

            Spacer(Modifier.height(32.dp))
            
            // Metric Cards Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    MetricCard(
                        icon = Icons.Rounded.Block,
                        value = state.cigarettesAvoided.toString(),
                        label = "Cigarettes avoided"
                    )
                }
                item {
                    MetricCard(
                        icon = Icons.Rounded.Shield,
                        value = "?",
                        label = "Money saved"
                    )
                }
                item {
                    MetricCard(
                        icon = Icons.Rounded.Shield,
                        value = state.cravingsDefeated.toString(),
                        label = "Cravings defeated"
                    )
                }
                item {
                    MetricCard(
                        icon = Icons.Rounded.MedicalServices,
                        value = state.nrtLogged.toString(),
                        label = "NRT logged"
                    )
                }
            }
            
            Button(
                onClick = onInsightsClick, 
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B856E))
            ) {
                Text("VIEW INSIGHTS", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun MetricCard(icon: ImageVector, value: String, label: String) {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1.1f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF011113))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Color(0xFF8FDCD0), modifier = Modifier.size(24.dp))
            Spacer(Modifier.weight(1f))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = Color(0xFF82918B), lineHeight = 16.sp)
        }
    }
}
