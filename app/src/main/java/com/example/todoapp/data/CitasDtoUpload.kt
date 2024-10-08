package com.example.todoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CitasDtoUpload (
    @SerialName("name") val name: String,
    @SerialName("date") val date: String
)