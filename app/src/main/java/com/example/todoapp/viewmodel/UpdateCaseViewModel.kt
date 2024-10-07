package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.CaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateCaseViewModel @Inject constructor(
    private val caseRepository: CaseRepository
): ViewModel() {
    fun updateCase(casoId: Int, delito: String, estado: String, clienteId: Int) {
        if (delito.isNotEmpty() && estado.isNotEmpty()){
            viewModelScope.launch {
                caseRepository.updateCaso(casoId, delito, estado, clienteId)
            }
        }
    }
}