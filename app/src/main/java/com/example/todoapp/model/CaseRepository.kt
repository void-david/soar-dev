package com.example.todoapp.model

import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto

interface CaseRepository {
    suspend fun getCasos() : List<CasoDto>
    suspend fun getCaso(id: Int) : CasoDto
    suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto>
}