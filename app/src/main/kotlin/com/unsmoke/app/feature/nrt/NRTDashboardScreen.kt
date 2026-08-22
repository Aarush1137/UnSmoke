package com.unsmoke.app.feature.nrt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Medication
import com.unsmoke.app.feature.empty.EmptyStateCard
import androidx.compose.material3.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import com.unsmoke.app.core.designsystem.AppColors
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("NRT Tracker", fontWeight = FontWeight.Bold, color = AppColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = AppColors.Mint)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.toggleLogSheet(true) },
                icon = { Icon(Icons.Rounded.Add, contentDescription = null, tint = androidx.compose.ui.graphics.Color.Black) },
                text = { Text("LOG NRT", color = androidx.compose.ui.graphics.Color.Black) },
                containerColor = AppColors.Mint
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
                        Text(state.todayUnitCount.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("NRT logged", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    
                    val progress = (state.todayUnitCount / 10f).coerceAtMost(1f)
                    PlanDonutRing(progress = progress)
                }
            }

            Spacer(Modifier.height(32.dp))
            if (state.recommendation != null) {
                Spacer(Modifier.height(32.dp))
                Text("Clinical Plan", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Medication, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                            Spacer(Modifier.width(8.dp))
                            Text("Week ${state.recommendation?.weekNumber}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Dose: ${state.recommendation?.dosage}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        Text("Frequency: ${state.recommendation?.frequency}", color = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(Modifier.height(8.dp))
                        Text(state.recommendation?.instructions ?: "", fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                    }
                }
            }

            Text("Today's log", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            
            if (state.todayUnitCount == 0) {
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
                    onClick = { viewModel.logNRT(System.currentTimeMillis(), 1, 8, 3, "General") },
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









