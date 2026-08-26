package com.unsmoke.app.feature.buddy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import com.unsmoke.app.core.domain.repository.BuddyProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddyScreen(onBack: () -> Unit,
    viewModel: BuddyViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quit Buddies", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                val myProfile = state.myProfile
                
                item {
                    Spacer(Modifier.height(8.dp))
                    Text("Your Network", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Share your code to pair with multiple buddies.", color = Color.LightGray)
                    
                    Spacer(Modifier.height(24.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AppColors.Surface,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("YOUR CODE", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Text(myProfile?.pairingCode ?: "---", color = Color.White, fontSize = 48.sp, letterSpacing = 8.sp)
                        }
                    }
                    
                    Spacer(Modifier.height(24.dp))
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
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.sendBuddyRequest(inputCode) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        enabled = inputCode.length == 6
                    ) {
                        Text("SEND REQUEST", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                    }
                    if (state.error != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(state.error!!, color = AppColors.Amber)
                    }
                }

                if (state.pendingRequestProfiles.isNotEmpty()) {
                    item {
                        Text("Pending Requests", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    items(state.pendingRequestProfiles) { req ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("${req.name} wants to connect!", color = Color.White, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(16.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Button(
                                        onClick = { viewModel.rejectBuddyRequest(req.uid) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                                    ) { Text("Decline") }
                                    Button(
                                        onClick = { viewModel.acceptBuddyRequest(req.uid) },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) { Text("Accept") }
                                }
                            }
                        }
                    }
                }

                if (state.buddyProfiles.isNotEmpty()) {
                    item {
                        Text("Connected Buddies", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    items(state.buddyProfiles) { buddy ->
                        BuddyCard(buddy)
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.toggleSOS() },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (myProfile?.needsHelp == true) AppColors.Surface else AppColors.Amber)
                    ) {
                        Text(if (myProfile?.needsHelp == true) "CANCEL MY SOS" else "BROADCAST SOS TO ALL", color = if (myProfile?.needsHelp == true) Color.White else MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun BuddyCard(buddyProfile: BuddyProfile) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = AppColors.Surface,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("${buddyProfile.name}", color = MaterialTheme.colorScheme.primary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            
            val daysSmokeFree = if (buddyProfile.quitStartEpochMillis != null) {
                ((System.currentTimeMillis() - buddyProfile.quitStartEpochMillis) / (1000 * 60 * 60 * 24)).toInt()
            } else 0
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Smoke Free", color = Color.LightGray, fontSize = 14.sp)
                    Text("${daysSmokeFree} Days", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("NRT Used", color = Color.LightGray, fontSize = 14.sp)
                    Text("${buddyProfile.totalNrtConsumed} units", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }

            if (buddyProfile.needsHelp) {
                Spacer(Modifier.height(16.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AppColors.Amber.copy(alpha = 0.2f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Warning, contentDescription = null, tint = AppColors.Amber, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text("Needs your support!", color = AppColors.Amber, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}