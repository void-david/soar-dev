package com.example.todoapp.data


data class Empleado(
    val empleadoId: Int,
    val jefeId: Int?,
    val matricula: String,
    val estudiante: Boolean,
    val usuarioId: Int
)
