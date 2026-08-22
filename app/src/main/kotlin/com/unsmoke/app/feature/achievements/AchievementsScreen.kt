package com.unsmoke.app.feature.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors
import com.unsmoke.app.core.domain.engine.BadgeState
import com.unsmoke.app.core.domain.engine.BadgeTier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    onBack: () -> Unit,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements", fontWeight = FontWeight.Bold, color = AppColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = AppColors.Mint) }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "You've earned ${state.totalEarned} of ${state.badges.size} badges",
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 24.dp, start = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(state.badges) { item ->
                        BadgeCard(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun BadgeCard(item: AchievementItemState) {
    val isEarned = item.state == BadgeState.EARNED
    val bgColor = if (isEarned) AppColors.Surface else AppColors.Surface.copy(alpha = 0.5f)
    val iconColor = when {
        !isEarned -> Color.White.copy(alpha = 0.2f)
        item.badge.tier == BadgeTier.BRONZE -> Color(0xFFCD7F32)
        item.badge.tier == BadgeTier.SILVER -> Color(0xFFC0C0C0)
        item.badge.tier == BadgeTier.GOLD -> Color(0xFFFFD700)
        else -> AppColors.Teal // Platinum/Special
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isEarned) iconColor.copy(alpha = 0.1f) else Color.Transparent)
                    .border(2.dp, if (isEarned) iconColor else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isEarned) Icons.Rounded.EmojiEvents else Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = item.badge.title,
                fontWeight = FontWeight.Bold,
                color = if (isEarned) Color.White else Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = if (isEarned) item.badge.description else "Locked",
                color = if (isEarned) AppColors.Mint else Color.White.copy(alpha = 0.3f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 14.sp
            )
        }
    }
}



