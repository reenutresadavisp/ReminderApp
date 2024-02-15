package com.reenu.reminderapp

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    val ACTION_CANCEL = "cancel"
    val ACTION_COMPLETE = "complete"

    val completeIntent = Intent(appContext, ReminderBroadCastReceiver::class.java).apply {
        action = ACTION_COMPLETE
        putExtra(WORKER_INPUT_DATA_ID_KEY,inputData.getLong(WORKER_INPUT_DATA_ID_KEY,0))
    }
    val completePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(appContext, 0, completeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val cancelIntent = Intent(appContext, ReminderBroadCastReceiver::class.java).apply {
        action = ACTION_CANCEL
    }
    val cancelPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(appContext, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    override fun doWork(): Result {
        try {
            createNotification(
                appContext,
                inputData.getString(WORKER_INPUT_DATA_MSG_KEY).toString()
            )
        }finally {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("Worker stop reason : ", stopReason.toString())
            }
        }

        return Result.success()
    }

    private fun createNotification(context: Context, message: String) {

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setContentTitle("Reminder")
            .setSmallIcon(R.drawable.bell)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(R.drawable.mark, ACTION_COMPLETE, completePendingIntent)
            .addAction(R.drawable.close, ACTION_CANCEL, cancelPendingIntent)
            .setAutoCancel(false)
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