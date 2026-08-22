package com.unsmoke.app.feature.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.AutoGraph
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
import com.unsmoke.app.core.designsystem.UnSmokeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    onNavigateBack: () -> Unit,
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insights", fontWeight = FontWeight.Bold, color = UnSmokeColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = UnSmokeColors.Mint)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = UnSmokeColors.Background)
            )
        },
        containerColor = UnSmokeColors.Background
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = UnSmokeColors.Teal)
            }
        } else if (!state.hasData) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyStateCard(icon = Icons.Rounded.AutoGraph, title = "Not Enough Data", message = "We need more craving logs to calculate your patterns.")
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
                        text = "Understand your patterns. Make smarter choices.",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
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
                        value = "\%",
                        icon = Icons.Rounded.TrendingDown,
                        description = "Percentage of cravings successfully defeated."
                    )
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
        colors = CardDefaults.cardColors(containerColor = UnSmokeColors.Surface),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(UnSmokeColors.Teal.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = UnSmokeColors.Mint)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, color = UnSmokeColors.Mint, fontSize = 12.sp)
            }
        }
    }
}

