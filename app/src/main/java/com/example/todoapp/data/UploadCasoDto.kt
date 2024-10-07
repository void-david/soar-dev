package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadCasoDto(
    @SerialName("delito") val delito: String,
    @SerialName("estado") val estado: String,
    @SerialName("cliente_id") val clienteId: Int,
)