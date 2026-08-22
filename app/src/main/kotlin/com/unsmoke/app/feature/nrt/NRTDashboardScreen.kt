package com.unsmoke.app.feature.nrt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NRTDashboardScreen(
    onBack: () -> Unit,
    viewModel: NRTViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.toggleLogSheet(true) },
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text("LOG NRT") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("NRT Tracker", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
                Row(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Today (${state.nrtType})", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(state.todayLogCount.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("NRT logged", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    
                    val progress = (state.todayLogCount / 10f).coerceAtMost(1f)
                    PlanDonutRing(progress = progress)
                }
            }

            Spacer(Modifier.height(32.dp))
            Text("Today's log", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            
            if (state.todayLogCount == 0) {
                Text("No NRT logged today.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                Text("Logs will appear here in the final build...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }

    if (state.showLogSheet) {
        ModalBottomSheet(onDismissRequest = { viewModel.toggleLogSheet(false) }) {
            Column(modifier = Modifier.padding(24.dp).padding(bottom = 32.dp)) {
                Text("Log NRT Usage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.logNRT(8, 3) },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("SAVE LOG")
                }
            }
        }
    }
}

@Composable
fun PlanDonutRing(progress: Float, modifier: Modifier = Modifier) {
    val primary = MaterialTheme.colorScheme.primary
    val track = MaterialTheme.colorScheme.surfaceVariant
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.size(80.dp)) {
            val strokeWidth = 8.dp.toPx()
            drawArc(color = track, startAngle = -90f, sweepAngle = 360f, useCenter = false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
            drawArc(color = primary, startAngle = -90f, sweepAngle = 360f * progress, useCenter = false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${(progress * 100).toInt()}%", fontWeight = FontWeight.Bold, color = primary)
            Text("of plan", fontSize = 10.sp)
        }
    }
}
