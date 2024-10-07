package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.Citas
import com.example.todoapp.data.CitasDto
import com.example.todoapp.model.CitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCitasViewModel @Inject constructor(
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

    private fun CitasDto.asDomainModel(): Citas {
        return Citas(
            citasId = this.citasId,
            name = this.name,
            date = this.date,
        )
    }


}