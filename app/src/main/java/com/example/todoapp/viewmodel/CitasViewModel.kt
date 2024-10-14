package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Citas
import com.example.todoapp.data.CitasDto
import com.example.todoapp.model.CitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(
    private val citasRepository: CitasRepository
): ViewModel() {

    // StateFlow to hold the list of citas
    private val _citas = MutableStateFlow<List<Citas>>(listOf())
    val citas: StateFlow<List<Citas>> get() = _citas

    // Stateflow to hold the list of citasByUserId
    private val _citasByUserId = MutableStateFlow<List<Citas>>(listOf())
    val citasByUserId: StateFlow<List<Citas>> get() = _citasByUserId

    // Stateflow to hold the list of citasByDate
    private val _citasByDate = MutableStateFlow<List<Citas>>(listOf())
    val citasByDate: StateFlow<List<Citas>> get() = _citasByDate

    // StateFlow to hold the list of cita
    private val _cita = MutableStateFlow<Citas?>(null)
    val cita: StateFlow<Citas?> get() = _cita

    fun getCitas() {
        viewModelScope.launch {
            try {
                // Fetch the list of CitaDto from the repository
                val result = citasRepository.getCitas()
                // Map the result to the Cita domain model
                _citas.emit(result.map { it -> it.asDomainModel() })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun getCitasByUserId(userId: Int) {
        viewModelScope.launch {
            try {
                // Fetch the list of CitaDto from the repository
                val result = citasRepository.getCitas()

                // Filter the list of CitaDto by userId
                val filteredResult = result.filter { it.clienteUserId == userId }
                Log.d("CitasViewModel", "UserId: $userId")
                Log.d("CitasViewModel", "Filtered Result: $filteredResult")

                // Map the filtered result to the Cita domain model
                _citasByUserId.emit(filteredResult.map { it -> it.asDomainModel() })
            } catch (e: Exception) {
                Log.d("CitasViewModel", "Error: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun getCita(id: Int) {
        viewModelScope.launch {
            try {
                // Fetch the list of CitaDto from the repository
                val result = citasRepository.getCita(id)
                // Map the result to the Cita domain model
                _cita.value = result.asDomainModel()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun insertCita(
        asunto: String,
        hora: Int,
        minuto: Int,
        fecha: String,
        clienteUsername: String,
        clienteUserId: Int,

        ){
        if (asunto.isNotEmpty() && fecha.isNotEmpty()){
            viewModelScope.launch {
                val cita = Citas(
                    id = null,
                    asunto = asunto,
                    hora = hora,
                    minuto = minuto,
                    fecha = fecha,
                    clienteUsername = clienteUsername,
                    clienteUserId = clienteUserId

                )
                citasRepository.insertCita(cita)
            }
        }
    }

    fun updateCita(
        citasId: Int,
        asunto: String,
        hora: Int,
        minuto: Int,
        fecha: String,
        clienteUsername: String,
        clienteUserId: Int,
    ){
        val cita = Citas(
            id = citasId,
            asunto = asunto,
            hora = hora,
            minuto = minuto,
            fecha = fecha,
            clienteUsername = clienteUsername,
            clienteUserId = clienteUserId
        )
        viewModelScope.launch {
            citasRepository.updateCita(cita, citasId)
        }
    }

    fun updateCitaByTimeAndHour(date: String, hour: Int, citasId: Int){
        viewModelScope.launch{
            citasRepository.updateCitaTimeAndHour(date, hour, citasId)
        }

    }

    fun deleteCita(citasId: Int){
        viewModelScope.launch {
            citasRepository.deleteCita(citasId)
        }
    }

    private fun CitasDto.asDomainModel(): Citas {
        return Citas(
            id = this.id,
            asunto = this.asunto,
            hora = this.hora,
            minuto = this.minuto,
            fecha = this.fecha,
            clienteUsername = this.clienteUsername,
            clienteUserId = this.clienteUserId
        )
    }


}