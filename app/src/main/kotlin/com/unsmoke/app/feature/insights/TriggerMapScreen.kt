package com.unsmoke.app.feature.insights

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerMapScreen(
    onBack: () -> Unit,
    viewModel: TriggerMapViewModel = hiltViewModel()
) {
    val cravings by viewModel.cravingsWithLocation.collectAsStateWithLifecycle()
    
    val defaultLocation = LatLng(37.7749, -122.4194)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    LaunchedEffect(cravings) {
        val target = cravings.firstOrNull { it.latitude != null && it.longitude != null }
        if (target != null) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(target.latitude!!, target.longitude!!), 12f
            )
        }
    }

    val heatmapProvider = remember(cravings) {
        val data = cravings.mapNotNull { craving ->
            if (craving.latitude != null && craving.longitude != null) {
                WeightedLatLng(LatLng(craving.latitude, craving.longitude), craving.intensity.toDouble())
            } else null
        }
        if (data.isNotEmpty()) {
            HeatmapTileProvider.Builder()
                .weightedData(data)
                .radius(50)
                .build()
        } else null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trigger Heatmap", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
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
                if (heatmapProvider != null) {
                    TileOverlay(tileProvider = heatmapProvider)
                }
            }
        }
    }
}