package com.reenu.reminderapp

import android.Manifest
import android.app.AlarmManager
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint


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
        routeState.value = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: Route.LIST.value
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
                ReminderDetail(viewModel = hiltViewModel()) {title,duration ->
                    navController.navigate(Route.LIST.value)
                }
            }
        }
    }


    fun createNotification(title: String, message: String) {

        val notification = NotificationCompat.Builder(this, "reminder_channel")
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(1001, notification)
    }

    fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1002
                )
            }
        }
    }


}
