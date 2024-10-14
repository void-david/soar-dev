package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.OptionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteOptionViewModel @Inject constructor(
    private val optionRepository: OptionsRepository
): ViewModel() {

    fun deleteTitle(titulo: String) {
        viewModelScope.launch {
            optionRepository.deleteTituloOption(titulo)
        }
    }

    fun deleteCategory(categoria: String) {
        viewModelScope.launch {
            optionRepository.deleteCategoriaOption(categoria)
        }
    }

}