package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Caso
import com.example.todoapp.model.CaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCaseViewModel @Inject constructor(
    private val caseRepository: CaseRepository
) : ViewModel() {

    fun createCase(delito: String, estado: String, clienteId: Int) {
        if (delito.isNotEmpty() && estado.isNotEmpty()) {
            viewModelScope.launch {
                val caso = Caso(
                    casoId = null,
                    delito = delito,
                    estado = estado,
                    clienteId = clienteId,
                    categoria = "",
                    fecha = "",
                    abogadoId = 0,
                    tipo = ""
                )
                caseRepository.insertCaso(caso)
            }
        }
    }
}