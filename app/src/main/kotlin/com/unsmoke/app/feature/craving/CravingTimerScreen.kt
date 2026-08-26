package com.unsmoke.app.feature.craving

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.unsmoke.app.core.designsystem.AppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.math.sin

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CravingTimerScreen(
    onDefeated: () -> Unit,
    onSmoked: () -> Unit,
    onChatWithCoach: () -> Unit,
    viewModel: CravingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val targetEndTime by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(System.currentTimeMillis() + 600_000L) }
    var timeLeft by remember { mutableStateOf(600) }
    var isBreathingMode by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLat by remember { mutableStateOf<Double?>(null) }
    var currentLng by remember { mutableStateOf<Double?>(null) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || 
                      permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { loc ->
                if (loc != null) {
                    currentLat = loc.latitude
                    currentLng = loc.longitude
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (hasFine || hasCoarse) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { loc ->
                if (loc != null) {
                    currentLat = loc.latitude
                    currentLng = loc.longitude
                }
            }
        } else {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    LaunchedEffect(targetEndTime) {
        while (true) {
            val remaining = ((targetEndTime - System.currentTimeMillis()) / 1000).toInt()
            if (remaining <= 0) {
                timeLeft = 0
                break
            }
            timeLeft = remaining
            delay(1000)
        }
    }

    LaunchedEffect(state.step) {
        if (state.step == CravingStep.OUTCOME) onDefeated()
        if (state.step == CravingStep.LAPSE) onSmoked()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isBreathingMode) "4-7-8 Breathing" else "Ride it out", color = Color.White) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, tint = Color.White, contentDescription = null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            
            if (isBreathingMode) {
                com.unsmoke.app.core.designsystem.components.BreathingOrb()
            } else {
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(32.dp))
                BreathingWaveAnimation()
                Spacer(Modifier.height(48.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                                        Column(Modifier.padding(16.dp)) {
                        Text("Urge Surfing toolkit:", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Text("- Drink a glass of cold water slowly")
                        Text("- Follow the breathing visualizer")
                        Text("- Distract yourself for 5 minutes")
                    }
                }
            }

            Spacer(Modifier.weight(1f))
            
            Button(
                onClick = { isBreathingMode = !isBreathingMode },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isBreathingMode) "Stop Breathing Mode" else "Start 4-7-8 Breathing")
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onChatWithCoach,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Talk to AI Coach", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.resolveCraving("DEFEATED", currentLat, currentLng) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("I got through it", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = { viewModel.resolveCraving("SMOKED", currentLat, currentLng) }) {
                Text("I slipped up", color = Color.White.copy(alpha = 0.7f))
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun BreathingWaveAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveColor = MaterialTheme.colorScheme.secondary
    Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        val path = Path()
        val width = size.width
        val height = size.height
        val centerY = height / 2

        for (x in 0..width.toInt()) {
            val normalizedX = x / width
            val y = centerY + sin(normalizedX * 4 * Math.PI.toFloat() + phase) * 30f
            if (x == 0) path.moveTo(x.toFloat(), y)
            else path.lineTo(x.toFloat(), y)
        }
        drawPath(path, color = waveColor, style = Stroke(width = 8f))
    }
}