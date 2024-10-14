package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CitasDtoStatus (
    @SerialName("status") val status: String,
    @SerialName("abogadoResponsable") val abogadoResponsable: String,
)