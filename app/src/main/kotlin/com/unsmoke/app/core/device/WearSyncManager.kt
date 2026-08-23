package com.unsmoke.app.core.device

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WearSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataClient = Wearable.getDataClient(context)

    suspend fun syncQuitStatus(startEpoch: Long) {
        try {
            val putDataReq = PutDataMapRequest.create("/quit_status").apply {
                dataMap.putLong("START_EPOCH", startEpoch)
                dataMap.putLong("TIMESTAMP", System.currentTimeMillis())
            }.asPutDataRequest()
            putDataReq.setUrgent()
            dataClient.putDataItem(putDataReq).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}