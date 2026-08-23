package com.unsmoke.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.unsmoke.app.MainActivity
import com.unsmoke.app.R
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@AndroidEntryPoint
class StreakWidgetReceiver : AppWidgetProvider() {
    @Inject lateinit var quitAttemptRepo: QuitAttemptRepository
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_streak)
            val pendingResult = goAsync()
            scope.launch {
                try {
                    val activeAttempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
                    val days = activeAttempt?.let { CalculationEngine.smokeFreeDays(it.startEpochMillis) } ?: 0
                    views.setTextViewText(R.id.widget_days, days.toString())
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}

class CravingWidgetReceiver : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_craving)
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                // Note: Real deep linking can be added here
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widget_craving_root, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

@AndroidEntryPoint
class DashboardWidgetReceiver : AppWidgetProvider() {
    @Inject lateinit var quitAttemptRepo: QuitAttemptRepository
    @Inject lateinit var nrtRepo: NRTRepository
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_dashboard)
            
            // Intent to launch app to NRT log
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widget_btn_log_nrt, pendingIntent)

            val pendingResult = goAsync()
            scope.launch {
                try {
                    val activeAttempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
                    var nrtCount = 0
                    if (activeAttempt != null) {
                        val usages = nrtRepo.getUsage(activeAttempt.id).firstOrNull() ?: emptyList()
                        val startOfToday = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        nrtCount = usages.filter { it.timestamp >= startOfToday }.sumOf { it.quantity }
                    }
                    views.setTextViewText(R.id.widget_nrt_count, nrtCount.toString())
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}