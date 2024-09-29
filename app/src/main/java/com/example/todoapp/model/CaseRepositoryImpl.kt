package com.example.todoapp.model

import android.util.Log
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.EmpleadoDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    override suspend fun getCasoById(): CasoDto {
        TODO("Not yet implemented")
    }
}