package com.example.todoapp.classes

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create the notification builder
        val notificationBuilder = NotificationCompat.Builder(context, "notification_channel_id")
            .setContentTitle("Scheduled Notification")
            .setContentText("This is your scheduled notification!")
            .setSmallIcon(R.drawable.round_notifications_24) // Add a small icon for the notification
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Get the NotificationManager
        val notificationManager = NotificationManagerCompat.from(context)

        // Check for notification permission
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // If permission is granted, show the notification
            notificationManager.notify(1, notificationBuilder.build())
        } else {
            // Permission is not granted; you can handle it here if needed
            // e.g., log an error or show a message
            Log.e("NotificationReceiver", "Notification permission not granted.")
            // Consider notifying the user about the missing permission if needed
        }
    }
}