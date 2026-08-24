package com.unsmoke.app.feature.insights

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.unsmoke.app.core.designsystem.unSmokeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerMapScreen(
    onBack: () -> Unit,
    viewModel: TriggerMapViewModel = hiltViewModel()
) {
    val cravings by viewModel.cravingsWithLocation.collectAsStateWithLifecycle()
    
    // Default to a central position (e.g., center of US or a specific default)
    // We'll update camera once we have data
    val defaultLocation = LatLng(37.7749, -122.4194)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    LaunchedEffect(cravings) {
        if (cravings.isNotEmpty()) {
            val first = cravings.first()
            if (first.latitude != null && first.longitude != null) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(first.latitude, first.longitude), 12f
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trigger Heatmap", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                cravings.forEach { craving ->
                    if (craving.latitude != null && craving.longitude != null) {
                        Marker(
                            state = MarkerState(position = LatLng(craving.latitude, craving.longitude)),
                            title = "Craving (Intensity: ${craving.intensity})",
                            snippet = craving.trigger ?: "Unknown trigger"
                        )
                    }
                }
            }
        }
    }
}