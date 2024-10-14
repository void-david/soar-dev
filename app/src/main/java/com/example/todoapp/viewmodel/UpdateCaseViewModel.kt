package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.CaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableSharedFlow

@HiltViewModel
class UpdateCaseViewModel @Inject constructor(
    private val caseRepository: CaseRepository
): ViewModel() {
    var isCaseUpdated by mutableStateOf(false)

    fun updateCase(
        casoId: Int,
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
        if (delito.isNotEmpty() && estado.isNotEmpty()){
            try {
                viewModelScope.launch {
                    caseRepository.updateCaso(
                        casoId,
                        delito,
                        estado,
                        categoria,
                        tipo,
                        nuc,
                        nombreCliente,
                        supervisor,
                        password,
                        investigationUnit,
                        unitLocation,
                        fvAccess
                    )
                }
                isCaseUpdated = true
            } catch(e: Exception){Log.e("UpdatingCaseViewModel", "Error updating Caso: ${e.localizedMessage}", e)}

        }
    }
}