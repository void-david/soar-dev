package com.example.todoapp.model

import com.example.todoapp.data.CasoDto

interface CaseRepository {
    suspend fun getCasos() : List<CasoDto>
    suspend fun getCasoById() : CasoDto
}