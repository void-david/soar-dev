package com.example.todoapp.viewmodel

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
        cliente: String,
        hora: Int,
        minuto: Int,
        fecha: String
    ){
        if (asunto.isNotEmpty() && fecha.isNotEmpty()){
            viewModelScope.launch {
                val cita = Citas(
                    id = null,
                    asunto = asunto,
                    cliente = cliente,
                    hora = hora,
                    minuto = minuto,
                    fecha = fecha
                )
                citasRepository.insertCita(cita)
            }
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
            cliente = this.cliente,
            hora = this.hora,
            minuto = this.minuto,
            fecha = this.fecha
        )
    }


}