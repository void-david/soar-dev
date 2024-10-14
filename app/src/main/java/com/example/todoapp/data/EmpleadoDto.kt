package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoDto(
    @SerialName("empleado_id") val empleadoId: Int,
    @SerialName("jefe_id") val jefeId: Int?,
    @SerialName("matricula") val matricula: String,
    @SerialName("admin") val estudiante: Boolean,
    @SerialName("usuario_id") val usuarioId: Int
)

@Serializable
data class EmpleadoDtoUpload(
    @SerialName("jefe_id") val jefeId: Int?,
    @SerialName("matricula") val matricula: String,
    @SerialName("estudiante") val estudiante: Boolean,
    @SerialName("usuario_id") val usuarioId: Int?
)
