package com.unsmoke.app.feature.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF011113),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF011113) // Dark theme background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            
            // Hero Badge
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(Color(0xFF1D4943), CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0B856E), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("7", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD8AC60))
                }
            }
            
            Spacer(Modifier.height(24.dp))
            Text("One Week", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("7 days smoke-free", fontSize = 16.sp, color = Color(0xFFB6C4BF))
            Spacer(Modifier.height(8.dp))
            Text("Keep going!", fontSize = 16.sp, color = Color(0xFF8FDCD0), fontWeight = FontWeight.Medium)
            
            Spacer(Modifier.height(48.dp))
            
            // Grid Title
            Text("Your badges", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
            Spacer(Modifier.height(16.dp))
            
            val badges = listOf("First Day", "7", "30", "90", "180", "365")
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(badges) { badge ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(if (badge == "7" || badge == "First Day") Color(0xFF1D4943) else Color(0xFF0A2022), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                badge.replace("First Day", "1"), 
                                fontSize = 24.sp, 
                                fontWeight = FontWeight.Bold,
                                color = if (badge == "7" || badge == "First Day") Color(0xFFD8AC60) else Color(0xFF82918B)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            if (badge == "First Day") "First Day" else " Days", 
                            fontSize = 12.sp, 
                            color = Color(0xFFB6C4BF)
                        )
                    }
                }
            }
            
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B856E))
            ) {
                Text("VIEW ALL", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
