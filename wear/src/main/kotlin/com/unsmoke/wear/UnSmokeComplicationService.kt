package com.unsmoke.wear

import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.wear.watchface.complications.data.*
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

class UnSmokeComplicationService : SuspendingComplicationDataSourceService() {

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData? {
        val dataClient = Wearable.getDataClient(this)
        
        // Fetch the latest quit_status from the Data Layer
        var startEpochMillis: Long? = null
        try {
            val dataItems = dataClient.dataItems.await()
            for (item in dataItems) {
                if (item.uri.path == "/quit_status") {
                    val dataMapItem = com.google.android.gms.wearable.DataMapItem.fromDataItem(item)
                    val epoch = dataMapItem.dataMap.getLong("START_EPOCH", -1L)
                    if (epoch != -1L) startEpochMillis = epoch
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val text = if (startEpochMillis != null) {
            val days = ((System.currentTimeMillis() - startEpochMillis) / (1000 * 60 * 60 * 24)).toInt()
            "${days}d"
        } else {
            "--"
        }

        val title = "Smoke Free"

        val tapIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val tapPendingIntent = PendingIntent.getActivity(
            this, 0, tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val plainText = PlainComplicationText.Builder(text).build()
        val plainTitle = PlainComplicationText.Builder(title).build()
        val icon = MonochromaticImage.Builder(Icon.createWithResource(this, R.drawable.ic_launcher_foreground)).build()

        return when (request.complicationType) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(plainText, plainText)
                .setTitle(plainTitle)
                .setMonochromaticImage(icon)
                .setTapAction(tapPendingIntent)
                .build()
            ComplicationType.LONG_TEXT -> LongTextComplicationData.Builder(plainText, plainText)
                .setTitle(plainTitle)
                .setMonochromaticImage(icon)
                .setTapAction(tapPendingIntent)
                .build()
            else -> null
        }
    }

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        val plainText = PlainComplicationText.Builder("12d").build()
        val plainTitle = PlainComplicationText.Builder("Smoke Free").build()
        val icon = MonochromaticImage.Builder(Icon.createWithResource(this, R.drawable.ic_launcher_foreground)).build()

        return when (type) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(plainText, plainText)
                .setTitle(plainTitle)
                .setMonochromaticImage(icon)
                .build()
            ComplicationType.LONG_TEXT -> LongTextComplicationData.Builder(plainText, plainText)
                .setTitle(plainTitle)
                .setMonochromaticImage(icon)
                .build()
            else -> null
        }
    }
}