package com.unsmoke.app.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsmoke.app.core.designsystem.UnSmokeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var biometricEnabled by remember { mutableStateOf(false) }
    
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold, color = UnSmokeColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = UnSmokeColors.Mint) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = UnSmokeColors.Background)
            )
        },
        containerColor = UnSmokeColors.Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("App Preferences", color = UnSmokeColors.Mint, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            }
            item {
                SettingToggleItem(
                    title = "Daily Notifications",
                    subtitle = "Supportive check-ins and reminders",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
            item {
                SettingToggleItem(
                    title = "App Lock",
                    subtitle = "Require Biometric/PIN to open UnSmoke",
                    checked = biometricEnabled,
                    onCheckedChange = { biometricEnabled = it }
                )
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
            
            item {
                Text("Data & Privacy", color = UnSmokeColors.Mint, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            }
            item {
                SettingActionItem(
                    title = "Export My Data",
                    subtitle = "Download all your logs and journals locally.",
                    icon = Icons.Rounded.Download,
                    onClick = { /* Export Logic */ }
                )
            }
            item {
                SettingActionItem(
                    title = "Delete All Data",
                    subtitle = "Permanently erase everything. Cannot be undone.",
                    icon = Icons.Rounded.DeleteForever,
                    isDestructive = true,
                    onClick = { showDeleteConfirm = true }
                )
            }
        }
    }
    
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete everything?") },
            text = { Text("This will permanently delete your quit attempts, cravings, journals, and achievements. This cannot be undone.") },
            confirmButton = {
                Button(onClick = { showDeleteConfirm = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun SettingToggleItem(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = UnSmokeColors.Teal, checkedTrackColor = UnSmokeColors.Teal.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun SettingActionItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isDestructive: Boolean = false, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = if (isDestructive) Color(0xFFFF5252) else Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Icon(icon, contentDescription = null, tint = if (isDestructive) Color(0xFFFF5252) else UnSmokeColors.Mint)
        }
    }
}
