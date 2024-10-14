package com.example.todoapp.model

import android.util.Log
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class OptionsRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : OptionsRepository {
    override suspend fun getTituloOptions(): List<String> {
        return try {
            postgrest.from("Titulos")
                .select()
                .decodeList<String>()
        } catch (e: Exception) {
            Log.e("OptionsRepositoryImpl", "Error fetching Titulo options: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getCategoriaOptions(): List<String> {
        return try{
            postgrest.from("Categorias")
                .select()
                .decodeList<String>()
        } catch (e: Exception){
            Log.e("OptionsRepositoryImpl", "Error fetching Categoria options: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun addTituloOption(titulo: String) {
        try{
            postgrest.from("Titulos")
                .insert(titulo)
        } catch (e: Exception){
            Log.e("OptionsRepositoryImpl", "Error inserting Titulo option: ${e.localizedMessage}", e)
        }
    }

    override suspend fun addCategoriaOption(categoria: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTituloOption(titulo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoriaOption(categoria: String) {
        TODO("Not yet implemented")
    }

}