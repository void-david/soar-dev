package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.Empleado
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.model.CaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CaseViewModel @Inject constructor(
    private val caseRepository: CaseRepository
): ViewModel() {

    // Estados adicionales para controlar la UI durante el proceso de autenticación
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> get() = _errorMessage


    // StateFlow to hold the list of empleados
    private val _casos = MutableStateFlow<List<Caso>>(listOf())
    val casos: StateFlow<List<Caso>> get() = _casos

    init {
        getCaso()
    }

    // Function to fetch empleados
    fun getCaso() {
        viewModelScope.launch {
            try {
                // Fetch the list of CasoDto from the repository
                val result = caseRepository.getCasos()
                // Map the result to the Caso domain model
                _casos.emit(result.map { it -> it.asDomainModel() })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Mapping function from CasoDto to Caso
    private fun CasoDto.asDomainModel(): Caso {
        return Caso(
            casoId = this.casoId,
            delito = this.delito,
            estado = this.estado,
            clienteId = this.clienteId,
        )
    }
}