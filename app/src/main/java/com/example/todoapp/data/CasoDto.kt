package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CasoDto(
    @SerialName("caso_id") val casoId: Int?,
    @SerialName("delito") val delito: String,
    @SerialName("estado") val estado: String? = "abierto",
    @SerialName("categoria") val categoria: String? = "",
    @SerialName("tipo_victima") val tipo: String? = "",
    @SerialName("fecha") val fecha: String? = "",
    @SerialName("NUC") val nuc: String? = "",
    @SerialName("nombre_cliente") val nombreCliente: String? = "",
    @SerialName("fiscal_titular") val supervisor: String? = "",
    @SerialName("password_FV") val password: String? = "",
    @SerialName("unidad_investigacion") val investigationUnit: String? = "",
    @SerialName("dir_unidad_inv") val unitLocation: String? = "",
    @SerialName("acceso_FV") val fvAccess: String? = "",


)