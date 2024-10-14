package com.example.todoapp.views

import NotificationListScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.classes.NotificationHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun InboxView(navController: NavController, paddingValues: PaddingValues){
    val context = LocalContext.current
    val postNotificationPermission = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(Unit) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }


    val listaTareas =
        listOf(
            "Notificación 1",
            "Notificación 2",
            "Notificación 3",
            "Notificación 4",
            "Notificación 5"
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (postNotificationPermission.status.isGranted) {
                    val notificationTime: LocalDateTime = LocalDateTime.now().plusSeconds(10)
                    // LocalDateTime.of(1970, 1, 1, 0, 0)
                    // Schedule for 1 minute later
                    notificationHandler.scheduleNotification(notificationTime, "Notification Title", "Notification Message")
                } else {
                    postNotificationPermission.launchPermissionRequest()
                }
            }
        ) {
            Text("Show Notification")
        }
        NotificationListScreen()
    }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun InboxViewPreview(){
    val navController = rememberNavController()
    InboxView(navController = navController, paddingValues = PaddingValues())
}