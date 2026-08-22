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
    onCravingClick: () -> Unit,
    onProgressClick: () -> Unit,
    onNRTClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCheckInClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    val greeting = when (LocalDateTime.now().hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..20 -> "Good evening"
        else -> "Good night"
    }

    Scaffold(
        containerColor = Color(0xFFFAFAF8),
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(", ", fontWeight = FontWeight.Bold, color = Color(0xFF18201E)) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = Color(0xFF596560))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAFAF8))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFFAFAF8)) {
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
                ProgressRing(progress = 0.8f, size = 240.dp) // Dummy progress for now
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B856E))
                    Text(text = "Days Free", fontSize = 18.sp, color = Color(0xFF596560), fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Since ", color = Color(0xFF818A84), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(32.dp))
            
            // Two Metric Cards
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.weight(1f).aspectRatio(1.2f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2829))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Rounded.MoneyOff, contentDescription = null, tint = Color(0xFF8FDCD0), modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        Text(String.format("?%.0f", uiState.netMoneySaved), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(Modifier.height(4.dp))
                        Text("Money saved", fontSize = 12.sp, color = Color(0xFF82918B))
                    }
                }
                
                Card(
                    modifier = Modifier.weight(1f).aspectRatio(1.2f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2829))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Rounded.Block, contentDescription = null, tint = Color(0xFF8FDCD0), modifier = Modifier.size(24.dp))
                        Spacer(Modifier.weight(1f))
                        // Assuming uiState has cigarettes avoided, but let's mock it if it doesn't
                        Text("", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(Modifier.height(4.dp))
                        Text("Cigs avoided", fontSize = 12.sp, color = Color(0xFF82918B))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onCravingClick,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B856E))
            ) {
                Text("I HAVE A CRAVING", fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 1.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(text = uiState.currentQuote, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, color = Color(0xFF596560), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
