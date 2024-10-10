package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CitasDtoUpload (
    @SerialName("asunto") val asunto: String,
    @SerialName("hora") val hora: Int,
    @SerialName("minuto") val minuto: Int,
    @SerialName("fecha") val fecha: String,
    @SerialName("clienteUsername") val clienteUsername: String,
    @SerialName("clienteUserId") val clienteUserId: Int,
)