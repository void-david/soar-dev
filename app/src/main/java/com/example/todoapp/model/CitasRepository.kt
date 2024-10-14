package com.example.todoapp.model

import com.example.todoapp.data.Citas
import com.example.todoapp.data.CitasDto
import com.example.todoapp.data.CitasDtoStatus

interface CitasRepository {
    suspend fun getCitas() : List<CitasDto>
    suspend fun getCita(id: Int) : CitasDto
    suspend fun insertCita(citas: Citas):Boolean
    suspend fun updateCita(citas: Citas, citasid: Int):Boolean

    suspend fun updateStatus(status: String, abogadoResponsable: String, citasId: Int):Boolean
    suspend fun getStatus(citasId: Int): CitasDtoStatus

    suspend fun updateCitaTimeAndHour(date: String, hour: Int, citasid: Int):Boolean
    suspend fun deleteCita(citasId: Int):Boolean

}