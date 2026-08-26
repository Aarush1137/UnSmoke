package com.unsmoke.app.feature.nrt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Medication
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.CheckCircle
import com.unsmoke.app.feature.empty.EmptyStateCard
import androidx.compose.material3.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import com.unsmoke.app.core.designsystem.AppColors
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
                title = { Text("NRT Tracker", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.toggleLogSheet(true) },
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text("LOG NRT") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState())) {
            Text("Daily Allowance", fontSize = 28.sp, fontWeight = FontWeight.Bold)
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
                        Text("used so far", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    
                    val maxLimit = state.recommendation?.maxPiecesPerDay ?: 10
                    DailyAllowanceRing(current = state.todayUnitCount, max = maxLimit)
                }
            }

            Spacer(Modifier.height(32.dp))
            AnimatedVisibility(
                visible = state.recommendation != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 4 })
            ) {
                Column {
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
                                Text("Week ${state.recommendation?.weekNumber ?: 1}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                            }
                            Spacer(Modifier.height(16.dp))
                            Text("Dose: ${state.recommendation?.dosage ?: ""}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                            Text("Frequency: ${state.recommendation?.frequency ?: ""}", color = MaterialTheme.colorScheme.onTertiaryContainer)
                            Spacer(Modifier.height(8.dp))
                            Text(state.recommendation?.instructions ?: "", fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }

            Text("Today's log", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            
            if (state.todayLogs.isEmpty()) {
                Text("No NRT logged today. Great job keeping cravings managed!", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                state.todayLogs.forEach { log ->
                    NRTLogCard(log = log, onDelete = { viewModel.deleteLog(log.id) })
                    Spacer(Modifier.height(8.dp))
                }
            }
            Spacer(Modifier.height(80.dp)) // padding for FAB
        }
    }

    if (state.showLogSheet) {
        var quantity by remember { mutableIntStateOf(1) }
        var cravingBefore by remember { mutableFloatStateOf(5f) }
        var isSaved by remember { mutableStateOf(false) }

        ModalBottomSheet(onDismissRequest = { viewModel.toggleLogSheet(false) }) {
            Column(modifier = Modifier.padding(24.dp).padding(bottom = 32.dp)) {
                if (isSaved) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
                        Spacer(Modifier.height(16.dp))
                        Text("Log Saved successfully!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text("Log NRT Usage", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(24.dp))
                    
                    Text("Quantity", fontWeight = FontWeight.SemiBold)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Rounded.Remove, contentDescription = "Decrease")
                        }
                        Text(quantity.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = "Increase")
                        }
                    }
                    Spacer(Modifier.height(16.dp))

                    Text("Craving Intensity Before (1-10)", fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = cravingBefore,
                        onValueChange = { cravingBefore = it },
                        valueRange = 1f..10f,
                        steps = 8
                    )
                    Text("Intensity: ${cravingBefore.toInt()}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(32.dp))

                    Button(
                        onClick = {
                            isSaved = true
                            viewModel.logNRT(System.currentTimeMillis(), quantity, cravingBefore.toInt(), cravingBefore.toInt(), "General")
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Text("SAVE LOG")
                    }
                }
            }
        }
    }
}

@Composable
fun DailyAllowanceRing(current: Int, max: Int, modifier: Modifier = Modifier) {
    val progress = (current.toFloat() / max.toFloat()).coerceAtMost(1f)
    val isOverLimit = current > max
    val primaryColor = if (isOverLimit) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(1000, easing = FastOutSlowInEasing))

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.size(80.dp)) {
            val strokeWidth = 8.dp.toPx()
            drawArc(color = trackColor, startAngle = -90f, sweepAngle = 360f, useCenter = false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
            drawArc(color = primaryColor, startAngle = -90f, sweepAngle = 360f * animatedProgress, useCenter = false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$current / $max", fontWeight = FontWeight.Bold, color = primaryColor)
            Text("max limit", fontSize = 10.sp)
        }
    }
}

@Composable
fun NRTLogCard(log: NRTLogItem, onDelete: () -> Unit) {
    val formatter = DateTimeFormatter.ofPattern("h:mm a").withZone(ZoneId.systemDefault())
    val timeString = formatter.format(Instant.ofEpochMilli(log.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${log.quantity}x ${log.productName}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(timeString, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (log.cravingBefore != null) {
                Box(modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text("Craving: ${log.cravingBefore}/10", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onDelete) {
                Icon(Icons.Rounded.Delete, contentDescription = "Delete log", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}