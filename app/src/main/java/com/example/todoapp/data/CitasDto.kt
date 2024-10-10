package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitasDto (
    @SerialName("id") val id: Int?,
    @SerialName("asunto") val asunto: String,
    @SerialName("cliente") val cliente: String,
    @SerialName("hora") val hora: Int,
    @SerialName("minuto") val minuto: Int,
    @SerialName("fecha") val fecha: String

)