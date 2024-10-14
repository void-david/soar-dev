package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.OptionsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOptionViewModel @Inject constructor(
    private val optionsRepository: OptionsRepository
) : ViewModel() {
    fun addTituloOption(titulo: String) {
        try {
            viewModelScope.launch {
                optionsRepository.addTituloOption(titulo)
            }
        } catch (e: Exception) {
            Log.e("CreateOptionViewModel", "Error creating Titulo option: ${e.localizedMessage}", e)
        }
    }

    fun addCategoriaOption(categoria: String) {
        try {
            viewModelScope.launch {
                optionsRepository.addCategoriaOption(categoria)
            }
        } catch (e: Exception) {
            Log.e("CreateOptionViewModel", "Error creating Categoria option: ${e.localizedMessage}", e)
        }
    }
}
