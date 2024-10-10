package com.example.todoapp.model

import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto

interface CaseRepository {
    suspend fun getCasos() : List<CasoDto>
    suspend fun getCaso(id: Int) : CasoDto
    suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto>
    suspend fun insertCaso(caso: Caso): Boolean
    suspend fun updateCaso(
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
    )
    suspend fun deleteCaso(casoId: Int)
}