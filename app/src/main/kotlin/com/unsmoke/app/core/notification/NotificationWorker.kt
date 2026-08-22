package com.unsmoke.app.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalTime

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val quitAttemptRepo: QuitAttemptRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val activeAttempt = quitAttemptRepo.getActiveAttempt().firstOrNull() ?: return Result.success()
        val hour = LocalTime.now().hour
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannel(notificationManager)

        val (title, message) = when (hour) {
            in 6..10 -> Pair("Good Morning", "Protect the next 10 minutes today.")
            in 18..20 -> Pair("Evening Check-in", "How did today go? Take a moment to log your day.")
            else -> Pair("Stay Strong", "Remember why you started this journey.")
        }

        val notification = NotificationCompat.Builder(context, "unsmoke_daily")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Placeholder
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(hour, notification)

        return Result.success()
    }

    private fun createChannel(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "unsmoke_daily",
                "Daily Support",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily supportive messages and check-in reminders"
            }
            manager.createNotificationChannel(channel)
        }
    }
}
