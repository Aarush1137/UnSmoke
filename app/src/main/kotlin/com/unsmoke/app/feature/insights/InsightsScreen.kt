package com.unsmoke.app.feature.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import com.unsmoke.app.feature.empty.EmptyStateCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    onNavigateBack: () -> Unit,
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    // Using a hardcoded start time for demo purposes. In reality, this comes from QuitAttemptEntity.
    val dummyTimeElapsedMillis = 3 * 24 * 60 * 60 * 1000L + 5 * 60 * 60 * 1000L // 3 days, 5 hours

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insights & Recovery", fontWeight = FontWeight.Bold, color = AppColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = AppColors.Mint)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.Teal)
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

                item {
                    RecoveryTimeline(dummyTimeElapsedMillis)
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
                            value = "85%",
                            icon = Icons.Rounded.TrendingDown,
                            description = "Percentage of cravings successfully defeated."
                        )
                    }
                }
                
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

@Composable
private fun InsightCard(title: String, value: String, icon: ImageVector, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(AppColors.Teal.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = AppColors.Mint)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, color = AppColors.Mint, fontSize = 12.sp)
            }
        }
    }
}

data class HealthMilestone(val title: String, val desc: String, val thresholdMs: Long, val icon: ImageVector)

@Composable
fun RecoveryTimeline(elapsedMs: Long) {
    val milestones = listOf(
        HealthMilestone("20 Minutes", "Blood pressure & pulse normalize.", 20 * 60 * 1000L, Icons.Rounded.Favorite),
        HealthMilestone("8 Hours", "Carbon monoxide levels drop by 50%.", 8 * 60 * 60 * 1000L, Icons.Rounded.Air),
        HealthMilestone("48 Hours", "Sense of taste and smell start to return.", 48 * 60 * 60 * 1000L, Icons.Rounded.Restaurant),
        HealthMilestone("72 Hours", "Nicotine is fully out of your system.", 72 * 60 * 60 * 1000L, Icons.Rounded.CheckCircle),
        HealthMilestone("2 Weeks", "Lung function and circulation improve.", 14 * 24 * 60 * 60 * 1000L, Icons.Rounded.DirectionsRun)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        milestones.forEachIndexed { index, milestone ->
            val isAchieved = elapsedMs >= milestone.thresholdMs
            val color = if (isAchieved) AppColors.Mint else Color.DarkGray
            
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                // Line and Icon
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
                    Box(
                        modifier = Modifier.size(32.dp).background(color.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(milestone.icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                    }
                    if (index != milestones.lastIndex) {
                        Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(color.copy(alpha = 0.3f)))
                    }
                }
                
                Spacer(Modifier.width(16.dp))
                
                // Content
                Column(modifier = Modifier.padding(bottom = 24.dp)) {
                    Text(milestone.title, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(milestone.desc, color = if (isAchieved) Color.LightGray else Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }
}

