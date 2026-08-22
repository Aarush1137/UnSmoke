package com.unsmoke.app.feature.checkin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CheckInScreen(onComplete: () -> Unit) {
    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("How was your day?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = {}) { Text("😊", fontSize = 24.sp) }
                OutlinedButton(onClick = {}) { Text("🙂", fontSize = 24.sp) }
                OutlinedButton(onClick = {}) { Text("😐", fontSize = 24.sp) }
                OutlinedButton(onClick = {}) { Text("😟", fontSize = 24.sp) }
            }
            Spacer(Modifier.height(32.dp))
            Text("Did you smoke today?", fontWeight = FontWeight.Bold)
            Row {
                Button(onClick = {}) { Text("No") }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(onClick = {}) { Text("Yes") }
            }
            Spacer(Modifier.height(32.dp))
            Text("How strong were cravings?", fontWeight = FontWeight.Bold)
            Slider(value = 5f, onValueChange = {}, valueRange = 0f..10f)
            Spacer(Modifier.weight(1f))
            Button(onClick = onComplete, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("SAVE CHECK-IN")
            }
        }
    }
}
