package com.unsmoke.app.feature.progress

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProgressScreen(
    onInsightsClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Progress", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            // Tab selector stub
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                FilterChip(selected = true, onClick = {}, label = { Text("7 Days") })
                FilterChip(selected = false, onClick = {}, label = { Text("30 Days") })
                FilterChip(selected = false, onClick = {}, label = { Text("3 Months") })
                FilterChip(selected = false, onClick = {}, label = { Text("1 Year") })
            }

            Spacer(Modifier.height(32.dp))
            
            // Metrics
            MetricRow("Smoke-free days", state.smokeFreeDays.toString())
            MetricRow("Cigarettes avoided", state.cigarettesAvoided.toString())
            MetricRow("Money saved", "₹${state.moneySaved.toInt()}")
            MetricRow("Cravings defeated", state.cravingsDefeated.toString())
            MetricRow("NRT logged", state.nrtLogged.toString())
            
            Spacer(Modifier.height(48.dp))
            Button(onClick = onInsightsClick, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("VIEW INSIGHTS")
            }
        }
    }
}

@Composable
fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
}
