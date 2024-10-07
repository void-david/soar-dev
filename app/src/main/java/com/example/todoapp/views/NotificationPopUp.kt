import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.todoapp.MainActivity
import com.example.todoapp.R

@RequiresApi(Build.VERSION_CODES.O)
fun Notificacion(context: Context) {
    val channelId = "my_channel_id"
    val channelName = "My Channel"
    val importance = NotificationManager.IMPORTANCE_HIGH // This allows heads-up notifications
    val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
        description = "Channel description"
    }

    // Register the channel with the system
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)


    val notificationId = 1 // Unique ID for each notification

    // Use `context` instead of `this` here
    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification) // Add an icon for the notification
        .setContentTitle("New Notification")
        .setContentText("This is a notification shown outside the app.")
        .setPriority(NotificationCompat.PRIORITY_HIGH) // For heads-up notification
        .setAutoCancel(true) // Dismisses the notification when tapped

    // Create an intent that opens your app when the notification is tapped
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    // Set the intent to launch when the notification is tapped
    notificationBuilder.setContentIntent(pendingIntent)

    // Finally, show the notification
    notificationManager.notify(notificationId, notificationBuilder.build())
}


