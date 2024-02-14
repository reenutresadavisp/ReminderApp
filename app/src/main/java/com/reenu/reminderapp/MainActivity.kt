package com.reenu.reminderapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1002
const val WORKER_INPUT_DATA_MSG_KEY = "message"
const val WORKER_INPUT_DATA_ID_KEY = "reminder_id"

private enum class Route(val value: String) {
    LIST("list"), DETAIL("detail")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermission()
        setContent {
            val navController = rememberNavController()
            val routeState = remember { mutableStateOf(Route.LIST.value) }
            loadContentScreen(navController = navController, routeState = routeState)

        }

    }

    @Composable
    private fun loadContentScreen(
        navController: NavHostController,
        routeState: MutableState<String>
    ) {
        NavHost(
            navController = navController,
            startDestination = routeState.value
        ) {

            composable(Route.LIST.value) {
                ReminderList(viewModel = hiltViewModel()) {
                    navController.navigate(Route.DETAIL.value)
                }
            }

            composable(Route.DETAIL.value) {
                ReminderDetail(viewModel = hiltViewModel()) { reminderId, title, duration ->
                    createWorkRequest(reminderId, title ?: "", duration)
                    navController.navigateUp()
                }
            }
        }
    }


    private fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun createWorkRequest(
        reminderId: Long,
        message: String,
        timeDelayInSeconds: Duration?
    ) {
        val myWorkRequest = timeDelayInSeconds?.let {
            OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(it)
                .setInputData(
                    workDataOf(
                        WORKER_INPUT_DATA_ID_KEY to reminderId,
                        WORKER_INPUT_DATA_MSG_KEY to message
                    )
                )
                .build()
        }

        if (myWorkRequest != null) {
            WorkManager.getInstance(this).enqueue(myWorkRequest)
        }
    }


}
