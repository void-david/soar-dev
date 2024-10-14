package com.example.todoapp.data

import kotlinx.serialization.SerialName

data class CategoriaDto(
    @SerialName("categoria_id") val categoriaId: Int,
    @SerialName("categoria") val categoria: String
)
