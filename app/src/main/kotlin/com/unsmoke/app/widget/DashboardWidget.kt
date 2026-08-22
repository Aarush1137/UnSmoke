package com.unsmoke.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.material3.ColorProviders
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.*
import com.unsmoke.app.MainActivity

class DashboardWidget : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()
        val days = prefs[WidgetDataRepository.KEY_SMOKE_FREE_DAYS] ?: 0
        val cigs = prefs[WidgetDataRepository.KEY_CIGARETTES_AVOIDED] ?: 0f
        val money = prefs[WidgetDataRepository.KEY_MONEY_SAVED] ?: 0f
        val todayCravings = prefs[WidgetDataRepository.KEY_TODAY_CRAVINGS] ?: 0
        val defeated = prefs[WidgetDataRepository.KEY_TODAY_DEFEATED] ?: 0
        val hasAttempt = prefs[WidgetDataRepository.KEY_HAS_ACTIVE_ATTEMPT] ?: false

        val bgColor = Color(0xFF14211F)
        val primaryColor = Color(0xFF55D8C6)
        val textColor = Color(0xFFECF5F1)
        val secondaryText = Color(0xFFB6C4BF)
        val amberColor = Color(0xFFF1B75A)
        val successColor = Color(0xFF3ECC9A)

        GlanceTheme {
            Row(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(bgColor)
                    .cornerRadius(20.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT: Days counter
                Column(
                    modifier = GlanceModifier.defaultWeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = days.toString(),
                        style = TextStyle(
                            color = ColorProvider(primaryColor),
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "DAYS",
                        style = TextStyle(color = ColorProvider(secondaryText), fontSize = 9.sp)
                    )
                    Text(
                        text = "SMOKE-FREE",
                        style = TextStyle(color = ColorProvider(secondaryText), fontSize = 9.sp)
                    )
                }

                // Divider
                Box(
                    modifier = GlanceModifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(Color(0xFF263632))
                )

                // RIGHT: Metrics grid
                Column(
                    modifier = GlanceModifier.defaultWeight().padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cigarettes avoided
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🚭", style = TextStyle(fontSize = 14.sp))
                        Spacer(GlanceModifier.width(4.dp))
                        Column {
                            Text(
                                text = cigs.toInt().toString(),
                                style = TextStyle(color = ColorProvider(textColor), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "cigarettes avoided",
                                style = TextStyle(color = ColorProvider(secondaryText), fontSize = 9.sp)
                            )
                        }
                    }

                    Spacer(GlanceModifier.height(8.dp))

                    // Money saved
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "₹", style = TextStyle(color = ColorProvider(amberColor), fontSize = 14.sp, fontWeight = FontWeight.Bold))
                        Spacer(GlanceModifier.width(4.dp))
                        Column {
                            Text(
                                text = money.toInt().toString(),
                                style = TextStyle(color = ColorProvider(amberColor), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "money saved",
                                style = TextStyle(color = ColorProvider(secondaryText), fontSize = 9.sp)
                            )
                        }
                    }

                    Spacer(GlanceModifier.height(8.dp))

                    // Today's cravings
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(
                                text = "$defeated/$todayCravings cravings defeated today",
                                style = TextStyle(color = ColorProvider(successColor), fontSize = 9.sp)
                            )
                        }
                    }

                    Spacer(GlanceModifier.height(10.dp))

                    // Craving button
                    Box(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .background(primaryColor)
                            .cornerRadius(10.dp)
                            .padding(horizontal = 8.dp, vertical = 6.dp)
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
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

class DashboardWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DashboardWidget()

    override fun onUpdate(context: Context, appWidgetManager: android.appwidget.AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        WidgetUpdateWorker.scheduleImmediate(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        WidgetUpdateWorker.schedule(context)
    }
}
