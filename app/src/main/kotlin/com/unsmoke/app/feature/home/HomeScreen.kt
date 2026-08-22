package com.unsmoke.app.feature.home

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDateTime

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
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("$greeting, ${uiState.userName ?: ""}") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Rounded.Home, "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = onProgressClick, icon = { Icon(Icons.Rounded.TrendingUp, "Progress") }, label = { Text("Progress") })
                NavigationBarItem(selected = false, onClick = onCravingClick, icon = { Icon(Icons.Rounded.LocalFireDepartment, "Craving") }, label = { Text("Craving") })
                NavigationBarItem(selected = false, onClick = onNRTClick, icon = { Icon(Icons.Rounded.Medication, "NRT") }, label = { Text("NRT") })
                NavigationBarItem(selected = false, onClick = onProfileClick, icon = { Icon(Icons.Rounded.Person, "You") }, label = { Text("You") })
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "DAYS SMOKE-FREE", fontWeight = FontWeight.Bold)
            Text(text = "${uiState.smokeFreeDays}", fontSize = 64.sp)
            Text(text = "Since ${uiState.quitDateDisplay}")
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Color(0xFFE9A94B))) {
                        append("???")
                    }
                    append(String.format("%.0f", uiState.netMoneySaved))
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onCravingClick,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("I HAVE A CRAVING")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = uiState.currentQuote)
        }
    }
}
