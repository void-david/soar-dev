package com.example.todoapp.model

import android.util.Log
import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
import com.example.todoapp.data.UploadCasoDto
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CaseRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : CaseRepository {

    override suspend fun getCasos(): List<CasoDto> {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepositoryImpl", "Fetching Caso...")

                val result = postgrest.from("Caso")
                    .select()
                    .decodeList<CasoDto>()
                Log.d("CaseRepositoryImpl", "Fetched Caso: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepositoryImpl", "Error fetching Caso: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getCaso(id: Int): CasoDto {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepositoryImpl", "Fetching Caso id: $id...")

                val result = postgrest.from("Caso")
                    .select {
                        filter {
                            eq("caso_id", id)
                        }
                    }.decodeSingle<CasoDto>()
                Log.d("CaseRepositoryImpl", "Fetched Caso ${id}: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepositoryImpl", "Error fetching Caso: ${e.localizedMessage}", e)
            CasoDto(0, "", "", 0)
        }
    }

    override suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto> {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepositoryImpl", "Fetching CasoEmpleado...")

                val result = postgrest.from("Caso_Empleado")
                    .select {
                        filter {
                            eq("caso_id", id)
                        }
                    }.decodeList<CasoEmpleadoDto>()
                Log.d("CaseRepositoryImpl", "Fetched CasoEmpleado: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepositoryImpl", "Error fetching CasoEmpleado: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun insertCaso(caso: Caso): Boolean {
        return try {
            withContext(Dispatchers.IO){
                val casoDto = UploadCasoDto(
                    delito = caso.delito,
                    estado = caso.estado,
                    clienteId = caso.clienteId
                )
                postgrest.from("Caso").insert(casoDto)
                Log.d("CaseRepositoryImpl", "Inserted Caso: $casoDto")
                true
            }
        } catch (e: Exception) {
            Log.e("CaseRepositoryImpl", "Error inserting Caso: ${e.localizedMessage}", e)
            false
        }
    }

    override suspend fun updateCaso(casoId: Int, delito: String, estado: String, clienteId: Int) {
        withContext(Dispatchers.IO){
            postgrest.from("Caso")
                .update({
                    set("delito", delito)
                    set("estado", estado)
                    set("cliente_id", clienteId)
                }){
                    filter {
                        eq("caso_id", casoId)
                    }
                }
        }
    }

    override suspend fun deleteCaso(casoId: Int) {
        withContext(Dispatchers.IO) {
            postgrest.from("Caso")
                .delete {
                    filter {
                        eq("caso_id", casoId)
                    }
                }
        }
    }
}