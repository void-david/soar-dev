package com.example.todoapp.model

import android.util.Log
import com.example.todoapp.data.Citas
import com.example.todoapp.data.CitasDto
import com.example.todoapp.data.CitasDtoUpload
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

    override suspend fun insertCita(citas: Citas): Boolean {
        return try{
            withContext(Dispatchers.IO){
                val citasDto = CitasDtoUpload(
                    name = citas.name,
                    date = citas.date
                )
                postgrest.from("Citas").insert(citasDto)
                Log.d("CitasRepositoryImpl", "Inserted Cita: $citasDto")
                true
            }
        }catch (e: Exception){
            Log.e("CitasRepositoryImpl", "Error inserting Cita: ${e.localizedMessage}", e)
            false
        }
    }



}