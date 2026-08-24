package com.unsmoke.app.feature.craving

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CravingOutcomeScreen(onContinue: () -> Unit) {
    Scaffold(containerColor = MaterialTheme.colorScheme.primary) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("YOU GOT THROUGH IT.", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("+1 Craving Defeated", color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(48.dp))
            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("CONTINUE", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LapseScreen(onContinue: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("That happened.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("A slip is not a fall. You can restart your streak when you're ready, but your history and progress remain.", 
                color = MaterialTheme.colorScheme.onSurfaceVariant, 
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(Modifier.height(48.dp))
            Button(onClick = onContinue, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("RETURN HOME")
            }
        }
    }
}
