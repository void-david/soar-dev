package com.example.todoapp.model

import com.example.todoapp.data.Citas
import com.example.todoapp.data.CitasDto

interface CitasRepository {
    suspend fun getCitas() : List<CitasDto>
    suspend fun getCita(id: Int) : CitasDto
    suspend fun insertCita(citas: Citas):Boolean
    suspend fun updateCita(citas: Citas, citasid: Int):Boolean
    suspend fun deleteCita(citasId: Int):Boolean

}