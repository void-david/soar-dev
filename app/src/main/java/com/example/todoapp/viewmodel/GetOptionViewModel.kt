package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TituloDto
import com.example.todoapp.model.OptionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetOptionViewModel @Inject constructor(
    private val optionsRepository: OptionsRepository
): ViewModel() {
    private val _titulos = MutableStateFlow<List<String>>(listOf())
    var titulos = listOf<String>()

    private val _categorias = MutableStateFlow<List<String>>(listOf())
    var categorias = listOf<String>()

    init{
        getTituloOptions()
        getCategoriasOptions()
    }

    fun getTituloOptions(): List<String> {
        viewModelScope.launch{
            try{
                val result = optionsRepository.getTituloOptions()
                _titulos.emit(listOf(result.map { it -> it.asDomainModel() }.toString()))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        return titulos
    }

    fun getCategoriasOptions(): List<String>{
        viewModelScope.launch{
            try{
                _categorias.value = optionsRepository.getCategoriaOptions()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        return categorias
    }

    private fun TituloDto.asDomainModel(): String {
        return this.titulo
    }
}