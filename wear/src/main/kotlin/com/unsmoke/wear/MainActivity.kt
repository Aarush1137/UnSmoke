import kotlinx.coroutines.tasks.await
package com.unsmoke.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.compose.ui.Modifier
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class MainActivity : ComponentActivity(), DataClient.OnDataChangedListener {
    private var startEpochState by mutableStateOf<Long?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    timeText = { TimeText() }
                ) {
                    var currentScreen by remember { mutableStateOf("home") }
                    
                    if (currentScreen == "home") {
                        WearHomeScreen(
                            startEpoch = startEpochState,
                            onBreatheClick = { currentScreen = "craving" },
                            onSosClick = {
                                kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                                    val nodes = Wearable.getNodeClient(this@MainActivity).connectedNodes.kotlinx.coroutines.tasks.await()
                                    nodes.forEach { node ->
                                        Wearable.getMessageClient(this@MainActivity).sendMessage(node.id, "/sos_alert", ByteArray(0)).await()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        WearCravingScreen(
                            onBack = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
        
        Wearable.getDataClient(this).dataItems.addOnSuccessListener { items ->
            try {
                items.forEach { item ->
                    if (item.uri.path == "/quit_status") {
                        val dataMap = DataMapItem.fromDataItem(item).dataMap
                        startEpochState = dataMap.getLong("START_EPOCH")
                    }
                }
            } finally {
                items.release()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        try {
            dataEvents.forEach { event ->
                if (event.dataItem.uri.path == "/quit_status") {
                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                    startEpochState = dataMap.getLong("START_EPOCH")
                }
            }
        } finally {
            dataEvents.release()
        }
    }
}