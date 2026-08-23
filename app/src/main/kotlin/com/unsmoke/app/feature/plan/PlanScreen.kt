package com.unsmoke.app.feature.plan

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    onBack: () -> Unit,
    viewModel: PlanViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Quit Plan", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            
            Text("Why I Quit", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.FormatQuote, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.width(16.dp))
                    Text(state.quitReason, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                }
            }

            Spacer(Modifier.height(32.dp))

            Text("My Journey Goals", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(12.dp))
            GoalTimelineCard(shortTerm = state.shortTermGoal, longTerm = state.longTermGoal)

            Spacer(Modifier.height(32.dp))
            
            Text("My Known Triggers", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(12.dp))
            FlowRowWrapper(items = state.triggers.toList(), icon = Icons.Rounded.Warning)

            Spacer(Modifier.height(32.dp))

            Text("My Coping Mechanisms", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(12.dp))
            FlowRowWrapper(items = state.supports.toList(), icon = Icons.Rounded.HealthAndSafety)

            Spacer(Modifier.height(32.dp))
            
            if (state.emergencyContactName.isNotEmpty()) {
                Text("Emergency Contact", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(), 
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(state.emergencyContactName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                            Text(state.emergencyContactPhone, fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f))
                        }
                        IconButton(onClick = {}, modifier = Modifier.background(MaterialTheme.colorScheme.tertiary, CircleShape)) {
                            Icon(Icons.Rounded.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.onTertiary)
                        }
                    }
                }
                Spacer(Modifier.height(32.dp))
            }

            Button(
                onClick = {}, 
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("EDIT PLAN", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
fun FlowRowWrapper(items: List<String>, icon: ImageVector) {
    if (items.isEmpty()) {
        Text("No items added yet.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        return
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(12.dp))
                Text(item, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun GoalTimelineCard(shortTerm: String, longTerm: String) {
    var animated by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animated) 1f else 0f, 
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "GoalProgress"
    )

    LaunchedEffect(Unit) { animated = true }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Short term
            Row(verticalAlignment = Alignment.Top) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                    Box(modifier = Modifier.width(2.dp).height((40 * progress).dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)))
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Short-term Goal", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text(shortTerm.ifEmpty { "Not set" }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            
            // Long term
            Row(verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(MaterialTheme.colorScheme.tertiary))
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Long-term Vision", fontSize = 12.sp, color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold)
                    Text(longTerm.ifEmpty { "Not set" }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}