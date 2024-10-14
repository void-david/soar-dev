package com.example.todoapp.model

import com.example.todoapp.data.CategoriaDto
import com.example.todoapp.data.TituloDto

interface OptionsRepository {
    suspend fun getTituloOptions(): List<TituloDto>
    suspend fun getCategoriaOptions(): List<CategoriaDto>
    suspend fun addTituloOption(titulo: String)
    suspend fun addCategoriaOption(categoria: String)
    suspend fun deleteTituloOption(titulo: String)
    suspend fun deleteCategoriaOption(categoria: String)
}