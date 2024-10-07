package com.example.todoapp.model

import com.example.todoapp.data.NotificationDto


interface NotificationRepository {
    suspend fun getNotifications() : List<NotificationDto>
    suspend fun getNotification(id: Int) : NotificationDto
}