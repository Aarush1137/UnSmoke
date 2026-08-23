package com.unsmoke.app.feature.buddy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unsmoke.app.core.designsystem.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddyScreen(
    viewModel: BuddyViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quit Buddy", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Background)
            )
        },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = AppColors.Mint)
            } else {
                val myProfile = state.myProfile
                val buddyProfile = state.buddyProfile

                if (myProfile?.buddyUid == null) {
                    // Not paired yet
                    Text("Pair with a Buddy", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Share your code or enter a buddy's code to link your accounts.", color = Color.LightGray)
                    
                    Spacer(Modifier.height(32.dp))
                    
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AppColors.Surface,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("YOUR CODE", color = AppColors.Mint, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Text(myProfile?.pairingCode ?: "---", color = Color.White, fontSize = 48.sp, letterSpacing = 8.sp)
                        }
                    }

                    Spacer(Modifier.height(32.dp))
                    
                    var inputCode by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = inputCode,
                        onValueChange = { if (it.length <= 6) inputCode = it },
                        label = { Text("Enter Buddy's Code") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.pairWithCode(inputCode) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Mint),
                        enabled = inputCode.length == 6
                    ) {
                        Text("PAIR NOW", color = AppColors.Background, fontWeight = FontWeight.Bold)
                    }
                    
                    if (state.error != null) {
                        Spacer(Modifier.height(16.dp))
                        Text(state.error!!, color = AppColors.Amber)
                    }
                } else {
                    // Paired
                    Text("Buddy Connected!", color = AppColors.Mint, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    
                    Spacer(Modifier.height(48.dp))
                    
                    if (buddyProfile?.needsHelp == true) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = AppColors.Amber.copy(alpha = 0.2f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Rounded.Warning, contentDescription = null, tint = AppColors.Amber, modifier = Modifier.size(40.dp))
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text("Buddy needs help!", color = AppColors.Amber, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("They are having a strong craving.", color = Color.White)
                                }
                            }
                        }
                    } else {
                        Text("Your buddy is doing great.", color = Color.LightGray)
                    }
                    
                    Spacer(Modifier.weight(1f))
                    
                    Button(
                        onClick = { viewModel.toggleSOS() },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (myProfile.needsHelp) AppColors.Surface else AppColors.Amber)
                    ) {
                        Text(if (myProfile.needsHelp) "CANCEL SOS" else "SEND SOS", color = if (myProfile.needsHelp) Color.White else AppColors.Background, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}