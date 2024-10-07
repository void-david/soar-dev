package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitasDto (
    @SerialName("id") val citasId: Int,
    @SerialName("name") val name: String,
    @SerialName("date") val date: String
)