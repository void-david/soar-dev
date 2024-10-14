package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClienteDto (
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("usuario_id") val usuarioId: Int,
    @SerialName("ciudad") val ciudad: String,
    @SerialName("sector") val sector: String,
    @SerialName("calle") val calle: String,
    @SerialName("numero") val numero: String,
)

@Serializable
data class ClienteDtoUpload (
    @SerialName("usuario_id") val usuarioId: Int? = null,
    @SerialName("ciudad") val ciudad: String,
    @SerialName("sector") val sector: String,
    @SerialName("calle") val calle: String,
    @SerialName("numero") val numero: String,
)