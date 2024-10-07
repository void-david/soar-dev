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

    fun createCase(
        delito: String,
        estado: String,
        categoria: String,
        tipo: String,
        fecha: String,
        nuc: String,
        nombreCliente: String,
        supervisor: String,
        password: String,
        investigationUnit: String,
        unitLocation: String,
        fvAccess: String
    ) {
        if (delito.isNotEmpty() && estado.isNotEmpty()) {
            viewModelScope.launch {
                val caso = Caso(
                    casoId = null,
                    delito = delito,
                    estado = estado,
                    categoria = categoria,
                    fecha = fecha,
                    tipo = tipo,
                    nuc = nuc,
                    nombreCliente = nombreCliente,
                    supervisor = supervisor,
                    password = password,
                    investigationUnit = investigationUnit,
                    unitLocation = unitLocation,
                    fvAccess = fvAccess
                )
                caseRepository.insertCaso(caso)
            }
        }
    }
}