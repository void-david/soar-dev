package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    @SerialName("usuario_id") val usuarioId: Int,
    @SerialName("username") val username: String,
    @SerialName("contrasena") val password: String,
    @SerialName("telefono") val phone: Long,
    @SerialName("nombre") val name: String,
    @SerialName("apellido1") val lastName1: String,
    @SerialName("apellido2") val lastName2: String,
    @SerialName("rol") val role: String,
)

@Serializable
data class UsuarioDtoUpload(
    @SerialName("username") val username: String,
    @SerialName("contrasena") val password: String,
    @SerialName("telefono") val phone: Long,
    @SerialName("nombre") val name: String,
    @SerialName("apellido1") val lastName1: String,
    @SerialName("apellido2") val lastName2: String,
    @SerialName("rol") val role: String,
)

