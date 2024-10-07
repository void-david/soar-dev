package com.example.todoapp.data

import kotlinx.datetime.LocalDateTime

data class Notificacion (
    val id: Int,
    val fecha: LocalDateTime,
    val titulo: String,
    val mensaje: String
)