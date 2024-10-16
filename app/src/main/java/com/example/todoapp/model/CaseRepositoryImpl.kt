package com.example.todoapp.model

import android.os.Message
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
            CasoDto(
                casoId = 0,
                delito = "",
                estado = "",
                categoria = "",
                tipo = "",
                fecha = "",
                nuc = "",
                nombreCliente = "",
                supervisor = "",
                password = "",
                investigationUnit = "",
                unitLocation = "",
                fvAccess = ""
            )
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
                    categoria = caso.categoria,
                    tipo = caso.tipo,
                    fecha = caso.fecha,
                    nuc = caso.nuc,
                    nombreCliente = caso.nombreCliente,
                    supervisor = caso.supervisor,
                    password = caso.password,
                    investigationUnit = caso.investigationUnit,
                    unitLocation = caso.unitLocation,
                    fvAccess = caso.fvAccess
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

    override suspend fun updateCaso(
        casoId: Int,
        delito: String,
        estado: String,
        categoria: String,
        tipo: String,
        nuc: String,
        nombreCliente: String,
        supervisor: String,
        password: String,
        investigationUnit: String,
        unitLocation: String,
        fvAccess: String
        ) {
        Log.d("UpdatingCaseRepo", "Updating Caso with id: $casoId")
        try{
            withContext(Dispatchers.IO){
                postgrest.from("Caso")
                    .update({
                        set("delito", delito)
                        set("estado", estado)
                        set("categoria", categoria)
                        set("tipo_victima", tipo)
                        set("NUC", nuc)
                        set("nombre_cliente", nombreCliente)
                        set("fiscal_titular", supervisor)
                        set("password_FV", password)
                        set("unidad_investigacion", investigationUnit)
                        set("dir_unidad_inv", unitLocation)
                        set("acceso_FV", fvAccess)
                    }){
                        filter {
                            eq("caso_id", casoId)
                        }
                    }
            }
        } catch(e: Exception){
            Log.e("UpdatingCaseRepo", "Error updating Caso: ${e.localizedMessage}", e)
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