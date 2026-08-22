package com.unsmoke.app.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) },
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
        containerColor = Color(0xFF011113)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Spacer(Modifier.height(16.dp))
            
            Text("Preferences", fontSize = 18.sp, color = Color(0xFF55D8C6), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            SettingRow("Reset Onboarding / Baseline")
            SettingRow("Change Currency")
            SettingRow("Notifications")
            
            Spacer(Modifier.height(32.dp))
            Text("About", fontSize = 18.sp, color = Color(0xFF55D8C6), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            SettingRow("Medical Disclaimer")
            SettingRow("Privacy Policy")
            SettingRow("Terms of Service")
            
            Spacer(Modifier.weight(1f))
            Text("UnSmoke v1.0.0", color = Color(0xFF82918B), modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun SettingRow(label: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(label, fontSize = 16.sp, color = Color.White)
    }
    HorizontalDivider(color = Color(0xFF263A37))
}
