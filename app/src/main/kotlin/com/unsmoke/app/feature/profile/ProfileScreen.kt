package com.unsmoke.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ProfileScreen(
    onPlanClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("You", fontWeight = FontWeight.Bold, color = UnSmokeColors.Mint) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = UnSmokeColors.Background)
            )
        },
        containerColor = UnSmokeColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle, 
                contentDescription = null, 
                modifier = Modifier.size(80.dp),
                tint = UnSmokeColors.Teal
            )
            Spacer(Modifier.height(16.dp))
            Text(state.userName, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Smoke-free since \", color = UnSmokeColors.Mint)
            
            Spacer(Modifier.height(48.dp))
            
            ProfileMenuButton(
                text = "My Quit Plan", 
                icon = Icons.Rounded.LibraryBooks, 
                onClick = onPlanClick
            )
            Spacer(Modifier.height(16.dp))
            
            ProfileMenuButton(
                text = "Achievements", 
                icon = Icons.Rounded.EmojiEvents, 
                onClick = onAchievementsClick
            )
            Spacer(Modifier.height(16.dp))
            
            ProfileMenuButton(
                text = "Settings", 
                icon = Icons.Rounded.Settings, 
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun ProfileMenuButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = UnSmokeColors.Surface),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = UnSmokeColors.Mint)
            Spacer(Modifier.width(16.dp))
            Text(text, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Medium)
        }
    }
}
