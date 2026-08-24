package com.unsmoke.app.feature.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import com.unsmoke.app.feature.empty.EmptyStateCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    onNavigateBack: () -> Unit,
    onMapClick: () -> Unit = {},
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showTitrationDialog by remember { mutableStateOf(false) }
    var dropInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insights & Recovery", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(onClick = onMapClick, containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.background) {
                Icon(Icons.Rounded.Map, contentDescription = "View Trigger Map")
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Your Health Recovery",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
                    )
                    Text(
                        text = "See what's happening inside your body.",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                if (state.isVaping) {
                    item {
                        TitrationCard(
                            currentMg = state.currentNicotineStrengthMg,
                            onLogDrop = { showTitrationDialog = true }
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }
                item {
                    RecoveryTimeline(state.elapsedMillis, state.usesNRT)
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }

                if (!state.hasData) {
                    item {
                        EmptyStateCard(icon = Icons.Rounded.AutoGraph, title = "Not Enough Data", message = "Log more cravings to see your personal patterns.")
                    }
                } else {
                    item {
                        Text("Craving Patterns", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(16.dp))
                    }
                    
                    item {
                        InsightCard(
                            title = "Top Trigger",
                            value = state.topTrigger,
                            icon = Icons.Rounded.Psychology,
                            description = "Plan ahead when you encounter this."
                        )
                    }
                    
                    item {
                        InsightCard(
                            title = "High-Risk Time",
                            value = state.highRiskTime,
                            icon = Icons.Rounded.Schedule,
                            description = "Your cravings peak during this window."
                        )
                    }
                    
                    item {
                        InsightCard(
                            title = "Best Coping Strategy",
                            value = state.bestCopingStrategy,
                            icon = Icons.Rounded.Shield,
                            description = "This strategy has helped you the most."
                        )
                    }
                    
                    item {
                        InsightCard(
                            title = "Craving Success Rate",
                            value = "${state.successRate}%",
                            icon = Icons.Rounded.TrendingDown,
                            description = "Percentage of cravings successfully defeated."
                        )
                    }
                }
                
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }

    if (showTitrationDialog) {
        AlertDialog(
            onDismissRequest = { showTitrationDialog = false },
            title = { Text("Log Nicotine Drop") },
            text = {
                OutlinedTextField(
                    value = dropInput,
                    onValueChange = { dropInput = it },
                    label = { Text("New Strength (mg/ml)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            },
            confirmButton = {
                Button(onClick = {
                    val mg = dropInput.toDoubleOrNull()
                    if (mg != null) {
                        viewModel.logTitrationDrop(mg)
                    }
                    showTitrationDialog = false
                    dropInput = ""
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showTitrationDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun InsightCard(title: String, value: String, icon: ImageVector, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }
}

data class HealthMilestone(val title: String, val desc: String, val thresholdMs: Long, val icon: ImageVector)

@Composable
fun RecoveryTimeline(elapsedMs: Long, usesNRT: Boolean) {
    val milestones = listOf(
        HealthMilestone("20 Minutes", "Blood pressure & pulse normalize.", 20 * 60 * 1000L, Icons.Rounded.Favorite),
        HealthMilestone("8 Hours", "Carbon monoxide levels drop by 50%.", 8 * 60 * 60 * 1000L, Icons.Rounded.Air),
        HealthMilestone("48 Hours", "Sense of taste and smell start to return.", 48 * 60 * 60 * 1000L, Icons.Rounded.Restaurant),
        HealthMilestone(
            "72 Hours", 
            if (usesNRT) "NRT continues to stabilize cravings without smoke." else "Nicotine is fully out of your system.", 
            72 * 60 * 60 * 1000L, 
            Icons.Rounded.CheckCircle
        ),
        HealthMilestone("2 Weeks", "Lung function and circulation improve.", 14 * 24 * 60 * 60 * 1000L, Icons.Rounded.DirectionsRun)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        milestones.forEachIndexed { index, milestone ->
            val isAchieved = elapsedMs >= milestone.thresholdMs
            val color = if (isAchieved) MaterialTheme.colorScheme.primary else Color.DarkGray
            
            val prevThreshold = if (index == 0) 0L else milestones[index - 1].thresholdMs
            val progressToThis = if (isAchieved) 1f else if (elapsedMs > prevThreshold) {
                ((elapsedMs - prevThreshold).toFloat() / (milestone.thresholdMs - prevThreshold)).coerceIn(0f, 1f)
            } else 0f
            
            val animatedProgress by animateFloatAsState(
                targetValue = progressToThis,
                animationSpec = tween(1500, easing = FastOutSlowInEasing),
                label = "TimelineProgress"
            )

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
                    Box(
                        modifier = Modifier.size(32.dp).background(color.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(milestone.icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                    }
                    if (index != milestones.lastIndex) {
                        Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(Color.DarkGray.copy(alpha = 0.3f))) {
                            if (animatedProgress > 0f && !isAchieved) {
                                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(animatedProgress).background(MaterialTheme.colorScheme.primary))
                            } else if (isAchieved) {
                                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)))
                            }
                        }
                    }
                }
                
                Spacer(Modifier.width(16.dp))
                
                Column(modifier = Modifier.padding(bottom = 24.dp).fillMaxWidth()) {
                    Text(milestone.title, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(milestone.desc, color = if (isAchieved) Color.LightGray else Color.Gray, fontSize = 14.sp)
                    
                    if (!isAchieved && progressToThis > 0f) {
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = Color.DarkGray
                        )
                        Text("${(progressToThis * 100).toInt()}% completed", color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }

}
@Composable
fun TitrationCard(currentMg: Double?, onLogDrop: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Opacity, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Vape Titration", color = MaterialTheme.colorScheme.onTertiaryContainer, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(if (currentMg != null) "Current: ${currentMg}mg" else "No strength logged", color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f), fontSize = 14.sp)
            }
            Button(
                onClick = onLogDrop,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiaryContainer, contentColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text("Log Drop")
            }
        }
    }
}