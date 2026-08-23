package com.unsmoke.app.core.domain.engine

import android.content.Context
import androidx.core.content.FileProvider
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import kotlinx.coroutines.flow.firstOrNull
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ExportEngine {
    suspend fun generateExport(
        context: Context,
        quitAttemptId: Long,
        cravingRepo: CravingRepository,
        nrtRepo: NRTRepository
    ): android.net.Uri? {
        try {
            val cravings = cravingRepo.getCravings(quitAttemptId).firstOrNull() ?: emptyList()
            val usages = nrtRepo.getUsage(quitAttemptId).firstOrNull() ?: emptyList()
            
            val file = File(context.cacheDir, "unsmoke_clinical_export.csv")
            file.printWriter().use { out ->
                out.println("Type,Timestamp,Date,Time,Value,Trigger/Product,Notes")
                
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm").withZone(ZoneId.systemDefault())
                
                cravings.forEach { c ->
                    val dateTime = formatter.format(Instant.ofEpochMilli(c.timestamp))
                    out.println("Craving,${c.timestamp},${dateTime},${c.intensity},${c.trigger ?: "None"},${c.outcome}")
                }
                
                usages.forEach { u ->
                    val dateTime = formatter.format(Instant.ofEpochMilli(u.timestamp))
                    out.println("NRT,${u.timestamp},${dateTime},${u.quantity},Product_${u.productId},")
                }
            }
            
            return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
