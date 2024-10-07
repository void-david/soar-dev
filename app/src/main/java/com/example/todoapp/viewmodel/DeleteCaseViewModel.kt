package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.CaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteCaseViewModel @Inject constructor(
    private val caseRepository: CaseRepository
): ViewModel() {

    fun deleteCase(casoId: Int) {
        viewModelScope.launch {
            caseRepository.deleteCaso(casoId)
        }
    }
}