package com.unsmoke.app.feature.rewards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.data.database.entity.RewardGoalEntity
import com.unsmoke.app.core.designsystem.AppColors
import com.unsmoke.app.core.designsystem.unSmokeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    onNavigateBack: () -> Unit,
    viewModel: RewardsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Financial Goals", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add Goal")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Net Saved Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Money Saved", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "\\",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (uiState.goals.isEmpty()) {
                Box(Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Rounded.Star, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                        Spacer(Modifier.height(16.dp))
                        Text("No financial goals yet.", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        Text("Set a goal to treat yourself with the money you've saved!", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.goals) { goal ->
                        RewardGoalCard(
                            goal = goal,
                            currentSavings = uiState.netMoneySaved,
                            currencySymbol = uiState.currencySymbol,
                            onDelete = { viewModel.deleteGoal(goal.id) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddGoalDialog(
                currencySymbol = uiState.currencySymbol,
                onDismiss = { showAddDialog = false },
                onAdd = { name, amount ->
                    viewModel.addGoal(name, amount)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun RewardGoalCard(
    goal: RewardGoalEntity,
    currentSavings: Double,
    currencySymbol: String,
    onDelete: () -> Unit
) {
    val progress = (currentSavings / goal.targetAmount).coerceIn(0.0, 1.0)
    val isAchieved = progress >= 1.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isAchieved) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isAchieved) Icons.Rounded.Check else Icons.Rounded.Star,
                            contentDescription = null,
                            tint = if (isAchieved) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(goal.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            text = "\\",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
                }
            }

            Spacer(Modifier.height(16.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { progress.toFloat() },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = if (isAchieved) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
            )
            
            Spacer(Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isAchieved) "Fully Funded!" else "% Funded",
                    fontSize = 12.sp,
                    color = if (isAchieved) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                if (!isAchieved) {
                    val remaining = goal.targetAmount - currentSavings
                    Text(
                        text = "\\ left",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun AddGoalDialog(
    currencySymbol: String,
    onDismiss: () -> Unit,
    onAdd: (String, Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amountStr by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Financial Goal") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("What are you saving for?") },
                    placeholder = { Text("e.g. Vacation, PS5") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = amountStr,
                    onValueChange = { amountStr = it },
                    label = { Text("Target Amount ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountStr.toDoubleOrNull()
                    if (name.isNotBlank() && amount != null && amount > 0) {
                        onAdd(name.trim(), amount)
                    }
                },
                enabled = name.isNotBlank() && amountStr.toDoubleOrNull() != null
            ) {
                Text("Add Goal")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}