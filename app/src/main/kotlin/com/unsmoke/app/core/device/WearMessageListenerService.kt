package com.unsmoke.app.core.device

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class WearMessageListenerService : WearableListenerService() {
    @Inject lateinit var cravingRepo: CravingRepository
    @Inject lateinit var quitAttemptRepo: QuitAttemptRepository
    
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        when (messageEvent.path) {
            "/log_craving" -> {
                scope.launch {
                    val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
                    if (attempt != null) {
                        // Try to fetch the phone's current location for the heatmap
                        var latitude: Double? = null
                        var longitude: Double? = null

                        val hasFine = ContextCompat.checkSelfPermission(
                            this@WearMessageListenerService,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        val hasCoarse = ContextCompat.checkSelfPermission(
                            this@WearMessageListenerService,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (hasFine || hasCoarse) {
                            try {
                                val fusedClient = LocationServices.getFusedLocationProviderClient(
                                    this@WearMessageListenerService
                                )
                                val location = fusedClient.getCurrentLocation(
                                    Priority.PRIORITY_BALANCED_POWER_ACCURACY, null
                                ).await()
                                if (location != null) {
                                    latitude = location.latitude
                                    longitude = location.longitude
                                }
                            } catch (e: Exception) {
                                // Location unavailable — proceed without it
                            }
                        }

                        cravingRepo.logCraving(CravingEventEntity(
                            quitAttemptId = attempt.id,
                            timestamp = System.currentTimeMillis(),
                            intensity = 5,
                            trigger = "Wear OS (Urge Surfing)",
                            location = null,
                            latitude = latitude,
                            longitude = longitude,
                            intervention = "Breathing Exercise (Watch)",
                            outcome = "SURVIVED",
                            durationSeconds = 60L,
                            nrtUsedBefore = false,
                            mood = null
                        ))
                    }
                }
            }
            "/sos_alert" -> {
                val intent = Intent(this, Class.forName("com.unsmoke.app.MainActivity")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    putExtra("TRIGGER_SOS", true)
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}