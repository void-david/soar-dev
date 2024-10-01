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
                try {
                    Log.d("CreateCase", "Starting case creation...")

                    val caso = Caso(
                        casoId = null,
                        delito = delito,
                        estado = estado,
                        clienteId = clienteId
                    )

                    val success = caseRepository.insertCaso(caso)

                    if (success) {
                        Log.d("CreateCase", "Case created successfully")
                    } else {
                        Log.e("CreateCase", "Unknown Error")
                    }
                } catch (e: Exception) {
                    Log.e("CreateCase", "Error during case creation: ${e.localizedMessage}", e)
                }
            }
        } else {
            Log.e("CreateCase", "Invalid input: Delito or Estado is empty")
        }
    }
}
