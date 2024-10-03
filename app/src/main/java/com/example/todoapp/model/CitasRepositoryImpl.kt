package com.example.todoapp.model

import com.example.todoapp.data.CitasDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class CitasRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CitasRepository {
    override suspend fun getCitas(): List<CitasDto> {
        return postgrest.from("Citas").select().decodeList()

    }
    override suspend fun getCita(id: Int): CitasDto {
        return postgrest.from("Citas").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle()
    }


}