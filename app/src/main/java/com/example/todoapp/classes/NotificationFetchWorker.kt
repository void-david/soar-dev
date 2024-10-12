package com.example.todoapp.classes

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.model.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NotificationFetchWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Call your repository's function to fetch notifications from Supabase
            val notifications = notificationRepository.getNotifications()

            // Process the fetched notifications here if needed
            for (notification in notifications) {
                // Schedule or display the notification as needed
            }

            Result.success() // Indicate the work finished successfully
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure() // Return failure if an error occurred
        }
    }
}
