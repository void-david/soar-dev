package com.example.todoapp.model

import com.example.todoapp.data.CitasDto

interface CitasRepository {
    suspend fun getCitas() : List<CitasDto>
    suspend fun getCita(id: Int) : CitasDto
}