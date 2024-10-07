package com.example.todoapp.model

import com.example.todoapp.data.CitasDto
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitasRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CitasRepository {
    override suspend fun getCitas(): List<CitasDto> {
        return try{
            withContext(Dispatchers.IO){
                val result = postgrest.from("Citas").select().decodeList<CitasDto>()
                result
            }
        }catch (e: Exception){
            emptyList()
        }

    }
    override suspend fun getCita(id: Int): CitasDto {
        return try{
            withContext(Dispatchers.IO){
                val result = postgrest.from("Citas").select{
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<CitasDto>()
                result
            }
        }catch (e: Exception){
            CitasDto(0, "", "")


        }
    }


}