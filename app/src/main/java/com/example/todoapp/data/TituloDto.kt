package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TituloDto(
    @SerialName("titulo_id") val tituloId: Int,
    @SerialName("titulo") val titulo: String
    )
