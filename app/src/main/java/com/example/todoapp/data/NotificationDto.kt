package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("id") val id: Int,
    @SerialName("fecha") val fecha: Long,
    @SerialName("titulo") val titulo: String,
    @SerialName("descripcion") val mensaje: String,
)