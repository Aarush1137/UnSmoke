package com.unsmoke.app.feature.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.animation.core.animateFloat
import androidx.compose.material.icons.rounded.Healing
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.components.ProgressRing
import androidx.compose.material.icons.automirrored.rounded.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    onInsightsClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Progress", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp).verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(16.dp))

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
                ProgressRing(progress = 1.0f, size = 180.dp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.smokeFreeDays.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Days Free", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(Modifier.height(32.dp))
            
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Rounded.Block,
                            value = state.cigarettesAvoided.toString(),
                            label = "Cigarettes avoided"
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Rounded.Shield,
                            value = String.format("%s%.0f", state.currencySymbol, state.moneySaved),
                            label = "Money saved"
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Rounded.Shield,
                            value = state.cravingsDefeated.toString(),
                            label = "Cravings defeated"
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MetricCard(
                            icon = Icons.Rounded.MedicalServices,
                            value = state.nrtLogged.toString(),
                            label = "NRT logged"
                        )
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
            if (state.showCheckInPrompt) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Healing, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(Modifier.width(8.dp))
                            Text("Weekly Lung Check-in", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("It's been a week! Time to test your lung capacity improvement.", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Spacer(Modifier.height(16.dp))
                        
                        var showTimer by remember { mutableStateOf(false) }
                        if (showTimer) {
                            var isRunning by remember { mutableStateOf(false) }
                            var time by remember { mutableStateOf(0) }
                            
                            LaunchedEffect(isRunning) {
                                if (isRunning) {
                                    while (true) {
                                        kotlinx.coroutines.delay(1000)
                                        time++
                                    }
                                }
                            }
                            
                            Text("${time} seconds", fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            Spacer(Modifier.height(16.dp))
                            
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Button(onClick = { isRunning = !isRunning }) {
                                    Text(if (isRunning) "STOP" else "START")
                                }
                                if (!isRunning && time > 0) {
                                    Button(onClick = { 
                                        viewModel.submitCheckIn(time)
                                        showTimer = false
                                    }) {
                                        Text("SAVE")
                                    }
                                }
                            }
                        } else {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                TextButton(onClick = { viewModel.dismissCheckIn() }) {
                                    Text("LATER")
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = { showTimer = true }) {
                                    Text("START NOW")
                                }
                            }
                        }
                    }
                }
            }

            LungCapacityWidget(
                baseline = state.baselineBreathHold,
                current = state.currentBreathHold
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onInsightsClick, 
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("VIEW INSIGHTS", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.weight(1f))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
        }
    }
}
@Composable
fun LungCapacityWidget(
    baseline: Int,
    current: Int
) {
    val progress = if (baseline > 0) (current.toFloat() / baseline.toFloat()).coerceAtLeast(1f) else 1f
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (baseline <= 0) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Rounded.Healing, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                Spacer(Modifier.height(16.dp))
                Text("Lung Capacity", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(8.dp))
                Text("Take your first breath-hold test in the Check-In screen to start tracking your respiratory recovery!", color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            }
        } else {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Healing, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Lung Capacity", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(Modifier.height(16.dp))
                
                val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "lung")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 0.8f,
                    targetValue = 0.8f + (progress - 1f) * 0.2f,
                    animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                        animation = androidx.compose.animation.core.tween(2000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
                        repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
                    ),
                    label = "lung"
                )
                
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Rounded.Healing, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.align(Alignment.Center).size(32.dp))
                    }
                }
                
                Spacer(Modifier.height(16.dp))
                Text(
                    "Your lung capacity has improved by ${((progress - 1f) * 100).toInt()}%!",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
