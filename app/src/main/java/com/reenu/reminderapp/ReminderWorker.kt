package com.reenu.reminderapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        createNotification(
            appContext,
            inputData.getString("message").toString())

        return Result.success()
    }

    private fun createNotification(context: Context, message: String) {

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setContentTitle("Reminder")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(1001, notification)
    }

}