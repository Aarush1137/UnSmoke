package com.unsmoke.app.feature.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.unsmoke.app.core.network.UpdateChecker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onReset: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showNameDialog by remember { mutableStateOf(false) }
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showToneDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var isCheckingUpdates by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold, color = AppColors.Mint) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = AppColors.Mint)
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Profile & Preferences
            item {
                SettingsSectionHeader("Personalization")
            }
            item {
                SettingsClickableItem(
                    title = "Display Name",
                    subtitle = state.userName,
                    icon = Icons.Rounded.Person,
                    onClick = { showNameDialog = true }
                )
            }
            item {
                SettingsClickableItem(
                    title = "Currency Symbol",
                    subtitle = state.currencySymbol,
                    icon = Icons.Rounded.AttachMoney,
                    onClick = { showCurrencyDialog = true }
                )
            }
            item {
                SettingsClickableItem(
                    title = "App Theme",
                    subtitle = state.theme,
                    icon = Icons.Rounded.Palette,
                    onClick = { showThemeDialog = true }
                )
            }

            // Notifications
            item {
                Spacer(Modifier.height(8.dp))
                SettingsSectionHeader("Notifications & Motivation")
            }
            item {
                SettingsToggleItem(
                    title = "Daily Push Notifications",
                    subtitle = "Morning preparation & evening check-in reminders",
                    icon = Icons.Rounded.Notifications,
                    checked = state.notificationsEnabled,
                    onCheckedChange = { viewModel.toggleNotifications(it) }
                )
            }
            item {
                SettingsClickableItem(
                    title = "Coaching Voice & Tone",
                    subtitle = state.notificationStyle.replace('_', ' '),
                    icon = Icons.Rounded.RecordVoiceOver,
                    onClick = { showToneDialog = true }
                )
            }

            // Security & Privacy
            item {
                Spacer(Modifier.height(8.dp))
                SettingsSectionHeader("Security & Privacy")
            }
            item {
                SettingsToggleItem(
                    title = "Biometric App Lock",
                    subtitle = "Require fingerprint / face scan to open app",
                    icon = Icons.Rounded.Fingerprint,
                    checked = state.appLockEnabled,
                    onCheckedChange = { viewModel.toggleAppLock(it) }
                )
            }
            item {
                SettingsInfoBanner(
                    title = "100% Offline & Private",
                    subtitle = "Your addiction recovery data never leaves your device. No tracking, no ads, no cloud sync."
                )
            }

            // System & Updates
            item {
                Spacer(Modifier.height(8.dp))
                SettingsSectionHeader("Updates & About")
            }
            item {
                SettingsClickableItem(
                    title = "Check for Updates",
                    subtitle = if (isCheckingUpdates) "Checking GitHub releases..." else "Version ${state.version}",
                    icon = Icons.Rounded.SystemUpdate,
                    onClick = {
                        isCheckingUpdates = true
                        scope.launch {
                            try {
                                val release = UpdateChecker.checkForUpdate(state.version)
                                if (release != null) {
                                    Toast.makeText(context, "New version ${release.latestVersion} available!", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "UnSmoke is up to date (${state.version})", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Unable to check for updates right now.", Toast.LENGTH_SHORT).show()
                            } finally {
                                isCheckingUpdates = false
                            }
                        }
                    }
                )
            }

            // Danger Zone
            item {
                Spacer(Modifier.height(8.dp))
                SettingsSectionHeader("Data Management", color = Color(0xFFFF5252))
            }
            item {
                SettingsClickableItem(
                    title = "Export Data Backup",
                    subtitle = "Export your quit logs and journal as JSON",
                    icon = Icons.Rounded.Download,
                    onClick = {
                        Toast.makeText(context, "Exporting data to Downloads...", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            item {
                SettingsClickableItem(
                    title = "Reset All Data",
                    subtitle = "Permanently clear your history and restart journey",
                    icon = Icons.Rounded.DeleteForever,
                    isDestructive = true,
                    onClick = { showDeleteConfirm = true }
                )
            }
        }
    }

    // Name Dialog
    if (showNameDialog) {
        var nameInput by remember { mutableStateOf(state.userName) }
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Edit Display Name") },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Your Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateName(nameInput.trim())
                        showNameDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Teal)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Currency Dialog
    if (showCurrencyDialog) {
        val currencies = listOf("â‚¹" to "INR (â‚¹)", "$" to "USD ($)", "â‚¬" to "EUR (â‚¬)", "Â£" to "GBP (Â£)", "Â¥" to "JPY (Â¥)", "C$" to "CAD (C$)")
        AlertDialog(
            onDismissRequest = { showCurrencyDialog = false },
            title = { Text("Select Currency") },
            text = {
                Column {
                    currencies.forEach { (symbol, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateCurrencySymbol(symbol)
                                    showCurrencyDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.currencySymbol == symbol,
                                onClick = {
                                    viewModel.updateCurrencySymbol(symbol)
                                    showCurrencyDialog = false
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(label, fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showCurrencyDialog = false }) { Text("Close") }
            }
        )
    }

    // Theme Dialog
    if (showThemeDialog) {
        val themes = listOf("DARK" to "Dark (Midnight Teal)", "AMOLED" to "AMOLED Pure Black", "LIGHT" to "Light Mode", "SYSTEM" to "System Default")
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Select Theme") },
            text = {
                Column {
                    themes.forEach { (themeKey, themeLabel) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateTheme(themeKey)
                                    showThemeDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.theme == themeKey,
                                onClick = {
                                    viewModel.updateTheme(themeKey)
                                    showThemeDialog = false
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(themeLabel, fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showThemeDialog = false }) { Text("Close") }
            }
        )
    }

    // Tone Dialog
    if (showToneDialog) {
        val styles = listOf(
            "GENTLE" to "Gentle & Compassionate (Encouraging, calm)",
            "DIRECT" to "Direct & Scientific (Stats, facts, logic)",
            "TOUGH_LOVE" to "Tough Love (High accountability, direct)",
            "MINIMAL" to "Minimal (Short & simple reminders)"
        )
        AlertDialog(
            onDismissRequest = { showToneDialog = false },
            title = { Text("Coaching Tone & Style") },
            text = {
                Column {
                    styles.forEach { (styleKey, styleLabel) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateNotificationStyle(styleKey)
                                    showToneDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.notificationStyle == styleKey,
                                onClick = {
                                    viewModel.updateNotificationStyle(styleKey)
                                    showToneDialog = false
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(styleLabel, fontSize = 14.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showToneDialog = false }) { Text("Close") }
            }
        )
    }

    // Delete Confirm Dialog
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Reset All Data?", fontWeight = FontWeight.Bold) },
            text = {
                Text("This will erase all your history, cravings logs, NRT trackers, and quit progress. You will start over completely clean.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.wipeAllData {
                            Toast.makeText(context, "Data wiped. Restarting journey.", Toast.LENGTH_SHORT).show()
                            showDeleteConfirm = false
                            onReset()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252))
                ) {
                    Text("Erase & Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }
}
@Composable
private fun SettingsSectionHeader(title: String, color: Color = AppColors.Mint) {
    Text(
        text = title.uppercase(),
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun SettingsToggleItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF192825)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppColors.Mint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = AppColors.Mint, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppColors.Mint,
                    checkedTrackColor = AppColors.Teal.copy(alpha = 0.5f)
                )
            )
        }
    }
}
@Composable
private fun SettingsClickableItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF192825)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isDestructive) Color(0xFFFF5252).copy(alpha = 0.15f) else AppColors.Mint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (isDestructive) Color(0xFFFF5252) else AppColors.Mint,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(title, color = if (isDestructive) Color(0xFFFF5252) else Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                }
            }
            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = Color.Transparent, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun SettingsInfoBanner(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF163F3A).copy(alpha = 0.4f))
            .border(1.dp, AppColors.Mint.copy(alpha = 0.2f), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Icon(Icons.Rounded.Security, contentDescription = null, tint = AppColors.Mint, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, color = AppColors.Mint, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, lineHeight = 16.sp)
            }
        }
    }
}



