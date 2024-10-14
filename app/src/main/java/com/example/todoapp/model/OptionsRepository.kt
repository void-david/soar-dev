package com.example.todoapp.model

interface OptionsRepository {
    suspend fun getTituloOptions(): List<String>
    suspend fun getCategoriaOptions(): List<String>
    suspend fun addTituloOption(titulo: String)
    suspend fun addCategoriaOption(categoria: String)
    suspend fun deleteTituloOption(titulo: String)
    suspend fun deleteCategoriaOption(categoria: String)
}