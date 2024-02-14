package com.reenu.reminderapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.reenu.reminderapp.db.ReminderRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: ReminderRepository
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action.equals("complete")) {
                CoroutineScope(Dispatchers.Default).launch {
                    repository.updateReminder(it.getLongExtra(WORKER_INPUT_DATA_ID_KEY, 0))
                }
                Toast.makeText(context, "Reminder completed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Reminder cancelled", Toast.LENGTH_SHORT).show()
            }
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(1001)
        }
    }
}