package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CasoEmpleadoDto (
    @SerialName("caso_empleado_id") val casoEmpleadoId: Int,
    @SerialName("empleado_id") val empleadoId: Int,
    @SerialName("caso_id") val casoId: Int,

    )