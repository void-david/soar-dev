package com.example.todoapp.model

import android.util.Log
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
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
                Log.d("CaseRepository", "Fetching Caso...")

                val result = postgrest.from("Caso")
                    .select()
                    .decodeList<CasoDto>()
                Log.d("CaseRepository", "Fetched Caso: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepository", "Error fetching Caso: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getCaso(id: Int): CasoDto {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepository", "Fetching Caso id: $id...")

                val result = postgrest.from("Caso")
                    .select {
                        filter {
                            eq("caso_id", id)
                        }
                    }.decodeSingle<CasoDto>()
                Log.d("CaseRepository", "Fetched Caso ${id}: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepository", "Error fetching Caso: ${e.localizedMessage}", e)
            CasoDto(0, "", "", 0)
        }
    }

    override suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto> {
        return try {
            withContext(Dispatchers.IO){
                Log.d("CaseRepository", "Fetching CasoEmpleado...")

                val result = postgrest.from("Caso_Empleado")
                    .select {
                        filter {
                            eq("caso_id", id)
                        }
                    }.decodeList<CasoEmpleadoDto>()
                Log.d("CaseRepository", "Fetched CasoEmpleado: $result")
                result
            }
        } catch (e: Exception) {
            Log.e("CaseRepository", "Error fetching CasoEmpleado: ${e.localizedMessage}", e)
            emptyList()
        }
    }
}