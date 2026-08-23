package com.unsmoke.app.core.domain.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object ShareEngine {
    fun generateShareImage(
        context: Context,
        days: Int,
        money: Double,
        currency: String
    ): android.net.Uri? {
        try {
            val width = 1080
            val height = 1080
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            
            // Draw Background (Deep Teal/Dark)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.parseColor("#192825")
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            
            // Draw accent circle
            paint.color = Color.parseColor("#00E676") // Mint accent
            paint.alpha = 40
            canvas.drawCircle(width / 2f, height / 2f, 400f, paint)
            paint.alpha = 255
            
            // Draw Title
            paint.color = Color.WHITE
            paint.textSize = 80f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("I AM SMOKE-FREE!", width / 2f, 200f, paint)
            
            // Draw Days
            paint.color = Color.parseColor("#00E676") // Mint
            paint.textSize = 250f
            canvas.drawText(days.toString(), width / 2f, 500f, paint)
            
            paint.color = Color.WHITE
            paint.textSize = 60f
            canvas.drawText(if (days == 1) "Day" else "Days", width / 2f, 600f, paint)
            
            // Draw Money Saved
            val moneyStr = String.format("%.2f", money)
            paint.color = Color.LTGRAY
            paint.textSize = 50f
            canvas.drawText("I've saved $currency$moneyStr", width / 2f, 800f, paint)
            
            // Footer Branding
            paint.color = Color.GRAY
            paint.textSize = 40f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText("Created with UnSmoke", width / 2f, 1000f, paint)
            
            val file = File(context.cacheDir, "unsmoke_milestone.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            
            return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
