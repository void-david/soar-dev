package com.example.todoapp.data


data class Empleado(
    val empleadoId: Int,
    val jefeId: Int?,
    val matricula: String,
    val admin: Boolean,
    val usuarioId: Int
)
