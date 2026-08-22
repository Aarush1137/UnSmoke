package com.unsmoke.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.action.*
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.material3.ColorProviders
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.*
import com.unsmoke.app.MainActivity
import com.unsmoke.app.R

class StreakWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()
        val smokeFreeDays = prefs[WidgetDataRepository.KEY_SMOKE_FREE_DAYS] ?: 0
        val quitDateDisplay = prefs[WidgetDataRepository.KEY_QUIT_DATE_DISPLAY] ?: ""
        val hasAttempt = prefs[WidgetDataRepository.KEY_HAS_ACTIVE_ATTEMPT] ?: false

        val bgColor = Color(0xFF14211F)          
        val primaryColor = Color(0xFF55D8C6)     
        val textColor = Color(0xFFECF5F1)        
        val secondaryText = Color(0xFFB6C4BF)    
        val buttonColor = Color(0xFF163F3A)      
        val amberColor = Color(0xFFF1B75A)       

        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(20.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!hasAttempt) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = GlanceModifier.fillMaxSize()
                    ) {
                        Text(
                            text = "UnSmoke",
                            style = TextStyle(color = ColorProvider(primaryColor), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(GlanceModifier.height(8.dp))
                        Text(
                            text = "Tap to set up",
                            style = TextStyle(color = ColorProvider(secondaryText), fontSize = 12.sp)
                        )
                    }
                } else {
                    Column(
                        modifier = GlanceModifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = smokeFreeDays.toString(),
                            style = TextStyle(
                                color = ColorProvider(primaryColor),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "DAYS SMOKE-FREE",
                            style = TextStyle(
                                color = ColorProvider(secondaryText),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        if (quitDateDisplay.isNotEmpty()) {
                            Spacer(GlanceModifier.height(4.dp))
                            Text(
                                text = "Since $quitDateDisplay",
                                style = TextStyle(color = ColorProvider(secondaryText), fontSize = 10.sp)
                            )
                        }
                        Spacer(GlanceModifier.height(12.dp))
                        Box(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .background(primaryColor)
                                .cornerRadius(12.dp)
                                .padding(horizontal = 12.dp, vertical = 8.dp)
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
                            Text(
                                text = "I HAVE A CRAVING",
                                style = TextStyle(
                                    color = ColorProvider(Color(0xFF003731)),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

class StreakWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = StreakWidget()

    override fun onUpdate(context: Context, appWidgetManager: android.appwidget.AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        WidgetUpdateWorker.scheduleImmediate(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        WidgetUpdateWorker.schedule(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }
}
