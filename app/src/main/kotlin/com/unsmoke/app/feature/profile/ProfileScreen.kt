package com.unsmoke.app.feature.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.UnSmokeColors
import com.unsmoke.app.core.designsystem.AppColors

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
    val context = LocalContext.current
    var showEditReasonDialog by remember { mutableStateOf(false) }
    var showEditContactDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Identity", fontWeight = FontWeight.Bold, color = AppColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = AppColors.Mint)
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Rounded.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Header Profile Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF163F3A), Color(0xFF14211F))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(AppColors.Mint.copy(alpha = 0.15f))
                                .border(2.dp, AppColors.Mint, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null,
                                modifier = Modifier.size(52.dp),
                                tint = AppColors.Mint
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = state.userName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = if (state.hasActiveAttempt) "Smoke-Free Since ${state.smokeFreeSince}" else "No Active Quit Attempt",
                            fontSize = 14.sp,
                            color = AppColors.Mint,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Quick Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileStatCard(
                        title = "Days Free",
                        value = "${state.daysSmokeFree}",
                        icon = Icons.Rounded.Timer,
                        color = AppColors.Teal,
                        modifier = Modifier.weight(1f)
                    )
                    ProfileStatCard(
                        title = "Saved",
                        value = "${state.currencySymbol}${state.moneySaved.toInt()}",
                        icon = Icons.Rounded.Savings,
                        color = AppColors.Amber,
                        modifier = Modifier.weight(1f)
                    )
                    ProfileStatCard(
                        title = "Avoided",
                        value = "${state.cigarettesAvoided}",
                        icon = Icons.Rounded.SmokingRooms,
                        color = AppColors.Mint,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // My Motivation / Why I'm Quitting
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF192825))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Favorite, contentDescription = null, tint = Color(0xFFFF7597), modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("My Core Motivation", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        IconButton(onClick = { showEditReasonDialog = true }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit Motivation", tint = AppColors.Mint, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "\"${state.quitReason}\"",
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 22.sp
                    )
                }
            }

            // Emergency Anchor / Support Contact
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF192825))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Emergency, contentDescription = null, tint = Color(0xFFFF5252), modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Emergency Anchor", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        IconButton(onClick = { showEditContactDialog = true }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit Contact", tint = AppColors.Mint, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(Modifier.height(8.dp))

                    if (state.emergencyContactName.isNotBlank() && state.emergencyContactPhone.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(state.emergencyContactName, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                Text(state.emergencyContactPhone, color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
                            }
                            IconButton(
                                onClick = {
                                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${state.emergencyContactPhone}"))
                                    context.startActivity(callIntent)
                                },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(AppColors.Mint)
                            ) {
                                Icon(Icons.Rounded.Call, contentDescription = "Call Contact", tint = Color.Black)
                            }
                        }
                    } else {
                        Text(
                            "Add a supportive friend or family member to call when you face an overwhelming craving.",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Spacer(Modifier.height(10.dp))
                        Button(
                            onClick = { showEditContactDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Mint.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text("Set Anchor Contact", color = AppColors.Mint, fontSize = 13.sp)
                        }
                    }
                }
            }

            // Navigation Links
            item {
                Text("Features & Management", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))
            }

            item {
                ProfileNavItem(
                    title = "My Quit Plan",
                    subtitle = "Review baseline, goals & strategies",
                    icon = Icons.Rounded.LibraryBooks,
                    onClick = onPlanClick
                )
            }

            item {
                ProfileNavItem(
                    title = "Achievements & Badges",
                    subtitle = "Milestones and badges unlocked",
                    icon = Icons.Rounded.EmojiEvents,
                    onClick = onAchievementsClick
                )
            }

            item {
                ProfileNavItem(
                    title = "App Settings",
                    subtitle = "Notifications, theme, biometric lock & data",
                    icon = Icons.Rounded.Settings,
                    onClick = onSettingsClick
                )
            }
        }
    }

    // Edit Reason Dialog
    if (showEditReasonDialog) {
        var newReason by remember { mutableStateOf(state.quitReason) }
        AlertDialog(
            onDismissRequest = { showEditReasonDialog = false },
            title = { Text("Update Core Motivation") },
            text = {
                OutlinedTextField(
                    value = newReason,
                    onValueChange = { newReason = it },
                    label = { Text("Why are you quitting?") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateQuitReason(newReason)
                        showEditReasonDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Teal)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditReasonDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Edit Emergency Contact Dialog
    if (showEditContactDialog) {
        var contactName by remember { mutableStateOf(state.emergencyContactName) }
        var contactPhone by remember { mutableStateOf(state.emergencyContactPhone) }
        AlertDialog(
            onDismissRequest = { showEditContactDialog = false },
            title = { Text("Emergency Anchor Contact") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Contact Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateEmergencyContact(contactName, contactPhone)
                        showEditContactDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Teal)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditContactDialog = false }) { Text("Cancel") }
            }
        )
    }
}
@Composable
private fun ProfileStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF192825))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(8.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Spacer(Modifier.height(2.dp))
            Text(title, fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
        }
    }
}
@Composable
private fun ProfileNavItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF192825)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.Mint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = AppColors.Mint, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Icon(Icons.Rounded.ArrowForwardIos, contentDescription = null, tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(16.dp))
        }
    }
}


