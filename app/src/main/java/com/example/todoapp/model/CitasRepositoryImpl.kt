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
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("Citas").select().decodeList<CitasDto>()
                result
            }
        } catch (e: Exception) {
            emptyList()
        }

    }



    override suspend fun getCita(id: Int): CitasDto {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("Citas").select {
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<CitasDto>()
                result
            }
        } catch (e: Exception) {
            CitasDto(
                id = null,
                asunto = "",
                hora = 0,
                minuto = 0,
                fecha = "",
                clienteUsername = "",
                clienteUserId = 0
            )


        }
    }

    override suspend fun insertCita(citas: Citas): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val citasDto = CitasDtoUpload(
                    asunto = citas.asunto,
                    hora = citas.hora,
                    minuto = citas.minuto,
                    fecha = citas.fecha,
                    clienteUsername = citas.clienteUsername,
                    clienteUserId = citas.clienteUserId
                )
                postgrest.from("Citas").insert(citasDto)
                Log.d("CitasRepositoryImpl", "Inserted Cita: $citasDto")
                true
            }
        } catch (e: Exception) {
            Log.e("CitasRepositoryImpl", "Error inserting Cita: ${e.localizedMessage}", e)
            false
        }
    }

    override suspend fun updateCita(citas: Citas, citasId: Int): Boolean {
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Citas").update({
                    set("asunto", citas.asunto)
                    set("hora", citas.hora)
                    set("minuto", citas.minuto)
                    set("fecha", citas.fecha)
                    set("clienteUsername", citas.clienteUsername)
                    set("clienteUserId", citas.clienteUserId)
                    }){
                    filter {
                        eq("id", citasId)
                    }
                }
            }
            true
        } catch (e: Exception){
            Log.e("CitasRepositoryImpl", "Error updating Cita: ${e.localizedMessage}", e)
            false
        }
    }

    override suspend fun deleteCita(id: Int): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("Citas").delete {
                    filter {
                        eq("id", id)
                    }
                }
                true
            }
        } catch (e: Exception) {
            Log.e("CitasRepositoryImpl", "Error deleting Cita: ${e.localizedMessage}", e)
            false
        }

    }
}





