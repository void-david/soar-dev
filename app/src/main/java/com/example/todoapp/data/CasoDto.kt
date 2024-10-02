package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CasoDto (
    @SerialName("caso_id") val casoId: Int,
    @SerialName("delito") val delito: String,
    @SerialName("estado") val estado: String? = "abierto",
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("abogado_id") val abogadoId: Int? = 0,
    @SerialName("categoria") val categoria: String? = "",
    @SerialName("tipo_victima") val tipo: String? = "",
    @SerialName("fecha") val fecha: String? = "",
)