package com.unsmoke.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.*
import com.unsmoke.app.MainActivity

class CravingWidget : GlanceAppWidget() {
    @Composable
    override fun Content() {
        val primaryColor = Color(0xFF55D8C6)
        val bgColor = Color(0xFF163F3A)

        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(16.dp)
                    .clickable(
                        actionStartActivity(
                            Intent(LocalContext.current, MainActivity::class.java).apply {
                                action = "com.unsmoke.app.ACTION_CRAVING"
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = GlanceModifier
                            .size(40.dp)
                            .background(primaryColor.copy(alpha = 0.3f))
                            .cornerRadius(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🌿", style = TextStyle(fontSize = 18.sp))
                    }
                    Spacer(GlanceModifier.height(4.dp))
                    Text(
                        text = "CRAVING",
                        style = TextStyle(
                            color = ColorProvider(primaryColor),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Tap to handle it",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFB6C4BF)),
                            fontSize = 8.sp
                        )
                    )
                }
            }
        }
    }
}

class CravingWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CravingWidget()
}
