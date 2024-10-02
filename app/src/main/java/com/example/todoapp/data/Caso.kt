package com.example.todoapp.data

data class Caso (
    val casoId: Int,
    val delito: String,
    val categoria: String,
    val tipo: String,
    val fecha: String,
    val estado: String,
    val clienteId: Int,
    val abogadoId: Int,
)