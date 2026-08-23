package com.unsmoke.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.unsmoke.app.R
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.engine.CalculationEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StreakWidgetReceiver : AppWidgetProvider() {

    @Inject
    lateinit var quitAttemptRepo: QuitAttemptRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
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

class DashboardWidgetReceiver : AppWidgetProvider()
class CravingWidgetReceiver : AppWidgetProvider()
