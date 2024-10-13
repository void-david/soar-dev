package com.example.todoapp.model

import android.util.Log
import com.example.todoapp.data.NotificationDto
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : NotificationRepository {

    override suspend fun getNotifications(): List<NotificationDto> {
        return try {
            withContext(Dispatchers.IO){
                Log.d("NotificationsRepository", "Fetching Notification...")

                val result = postgrest.from("Notificaciones")
                    .select()
                    .decodeList<NotificationDto>()
                Log.d("NotificationsRepository", "Fetched Notifications: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("NotificationsRepository", "Error fetching Notifications: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getNotificationsByUserId(userId: Int): List<NotificationDto> {
        return try {
            withContext(Dispatchers.IO){
                Log.d("NotificationsRepository", "Fetching Notification...")

                val result = postgrest.from("Notificaciones")
                    .select{
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeList<NotificationDto>()
                Log.d("NotificationsRepository", "Fetched Notifications: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("NotificationsRepository", "Error fetching Notifications: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getNotification(id: Int): NotificationDto {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepository", "Fetching Caso id: $id...")

                val result = postgrest.from("Caso")
                    .select {
                        filter {
                            eq("caso_id", id)
                        }
                    }.decodeSingle<NotificationDto>()
                Log.d("CaseRepository", "Fetched Caso ${id}: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepository", "Error fetching Caso: ${e.localizedMessage}", e)
            NotificationDto(0, LocalDateTime(1970, 1, 1, 0, 0), "", "")
        }
    }
}