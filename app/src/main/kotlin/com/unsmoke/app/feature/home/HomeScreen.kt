package com.unsmoke.app.feature.home

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDateTime
import com.unsmoke.app.core.designsystem.components.ProgressRing

@Composable
fun HomeScreen(
    onRewardsClick: () -> Unit,
    onCravingClick: () -> Unit,
    onProgressClick: () -> Unit,
    onNRTClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCheckInClick: () -> Unit,
    onBuddyClick: () -> Unit,
    onCompanionClick: () -> Unit,
    onRelapseClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showNotifications by remember { mutableStateOf(false) }
    
    val greeting = when (LocalDateTime.now().hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..20 -> "Good evening"
        else -> "Good night"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("$greeting, ${uiState.userName}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground) },
                actions = {
                    IconButton(onClick = onCompanionClick) { Icon(androidx.compose.material.icons.Icons.Rounded.Face, contentDescription = "Virtual Pet", tint = MaterialTheme.colorScheme.primary) }
                    IconButton(onClick = onBuddyClick) { Icon(Icons.Rounded.People, contentDescription = "Buddy", tint = MaterialTheme.colorScheme.primary) }
                    IconButton(onClick = { showNotifications = true }) { Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Rounded.Home, "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = onProgressClick, icon = { Icon(Icons.Rounded.BarChart, "Progress") }, label = { Text("Progress") })
                NavigationBarItem(selected = false, onClick = onCravingClick, icon = { Icon(Icons.Rounded.Adjust, "Craving") }, label = { Text("Craving") })
                NavigationBarItem(selected = false, onClick = onNRTClick, icon = { Icon(Icons.Rounded.MedicalServices, "NRT") }, label = { Text("NRT") })
                NavigationBarItem(selected = false, onClick = onProfileClick, icon = { Icon(Icons.Rounded.Person, "You") }, label = { Text("You") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Hero Progress Ring
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                ProgressRing(progress = 1.0f, size = 240.dp) 
                uiState.startEpochMillis?.let { startEpoch ->
                    LiveTimerContent(startEpoch = startEpoch)
                } ?: run {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "0", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(text = "Days Free", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Since ${uiState.quitDateDisplay}", color = MaterialTheme.colorScheme.outlineVariant, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(32.dp))
            
            // Two Metric Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.weight(1f).aspectRatio(1.2f).clickable { onRewardsClick() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Rounded.MoneyOff, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        Text(String.format("%s%.0f", uiState.currencySymbol, uiState.netMoneySaved), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(4.dp))
                        Text("Money saved", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                
                Card(
                    modifier = Modifier.weight(1f).aspectRatio(1.2f).clickable { onProgressClick() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Rounded.Block, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        Text(uiState.cigarettesAvoided.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(4.dp))
                        Text("Cigs avoided", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onCravingClick,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("I HAVE A CRAVING", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary, letterSpacing = 1.sp)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Daily Quit Coach Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(Modifier.width(8.dp))
                        Text("Daily Quit Coach", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = uiState.dailyLesson,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI Quit Coach Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(Modifier.width(8.dp))
                        Text("Gemini AI Insights", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                    Spacer(Modifier.height(8.dp))
                    if (uiState.isAiLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    } else if (uiState.aiInsight != null) {
                        Text(
                            text = uiState.aiInsight!!,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    } else {
                        Button(
                            onClick = { viewModel.fetchAiInsight() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiaryContainer, contentColor = MaterialTheme.colorScheme.tertiaryContainer),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Analyze My Cravings", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.weight(1f).clickable { onNRTClick() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Rounded.Medication, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(8.dp))
                        Text("Log NRT", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f).clickable { onCheckInClick() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Rounded.Air, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(8.dp))
                        Text("Lung Test", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onRelapseClick) {
                Text("I slipped up and smoked...", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = uiState.currentQuote, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(48.dp))
        }
    }

    if (showNotifications) {
        @OptIn(ExperimentalMaterial3Api::class)
        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = { showNotifications = false },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Notifications", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.height(16.dp))
                // Dummy list of notifications
                NotificationItem("Check-in time!", "How are your lungs feeling today?", Icons.Rounded.MonitorHeart)
                NotificationItem("Tapering Alert", "You're scheduled to step down your NRT dose tomorrow.", Icons.Rounded.Timeline)
                NotificationItem("Badge Earned!", "3 Days Smoke Free", Icons.Rounded.WorkspacePremium)
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun NotificationItem(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background, androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        androidx.compose.material3.Icon(icon, contentDescription = null, tint = androidx.compose.material3.MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        Spacer(Modifier.width(16.dp))
        Column {
            androidx.compose.material3.Text(title, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
            androidx.compose.material3.Text(desc, fontSize = 14.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }}

@Composable
fun LiveTimerContent(startEpoch: Long) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }
    
    val diff = kotlin.math.max(0L, currentTime - startEpoch)
    val totalSeconds = diff / 1000
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = days.toString(), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(text = "Days Free", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
        
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TimeBlock(hours.toString().padStart(2, '0'), "h")
            Text(":", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            TimeBlock(minutes.toString().padStart(2, '0'), "m")
            Text(":", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            TimeBlock(seconds.toString().padStart(2, '0'), "s")
        }
    }
}

@Composable
fun TimeBlock(value: String, unit: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(text = value, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = unit, color = MaterialTheme.colorScheme.outline, fontSize = 12.sp, modifier = Modifier.padding(bottom = 2.dp))
    }
}