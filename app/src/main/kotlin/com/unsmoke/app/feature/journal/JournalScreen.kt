package com.unsmoke.app.feature.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.UnSmokeColors
import com.unsmoke.app.core.data.database.entity.DailyCheckInEntity
import com.unsmoke.app.feature.empty.EmptyStateCard
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    onBack: () -> Unit,
    onAddClick: () -> Unit,
    viewModel: JournalViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journal", fontWeight = FontWeight.Bold, color = UnSmokeColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = UnSmokeColors.Mint) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = UnSmokeColors.Background)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                containerColor = UnSmokeColors.Teal,
                contentColor = Color.White
            ) {
                Text("Daily Check-In", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = UnSmokeColors.Background
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = UnSmokeColors.Teal)
            }
        } else if (state.checkIns.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyStateCard(
                    icon = Icons.Rounded.Book,
                    title = "No Entries Yet",
                    message = "Reflecting on your journey helps you understand your triggers and stay grounded.",
                    ctaText = "Check In Now",
                    onCtaClick = onAddClick
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.checkIns) { checkIn ->
                    JournalCard(checkIn)
                }
            }
        }
    }
}

@Composable
private fun JournalCard(checkIn: DailyCheckInEntity) {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
    val dateStr = formatter.format(Instant.ofEpochMilli(checkIn.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = UnSmokeColors.Surface),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(dateStr, color = UnSmokeColors.Mint, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Day Rating: \/10", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            if (checkIn.tomorrowFocus.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Focus for tomorrow:", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Text(checkIn.tomorrowFocus, color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
