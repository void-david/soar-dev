package com.example.todoapp.data

data class Citas (
    val id: Int?,
    val asunto: String,
    val hora: Int,
    val minuto: Int,
    val fecha: String,
    val clienteUsername: String,
    val clienteUserId: Int,
)