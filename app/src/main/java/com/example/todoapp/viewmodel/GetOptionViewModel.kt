package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Categoria
import com.example.todoapp.data.CategoriaDto
import com.example.todoapp.data.Titulo
import com.example.todoapp.data.TituloDto
import com.example.todoapp.model.OptionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetOptionViewModel @Inject constructor(
    private val optionsRepository: OptionsRepository
): ViewModel() {
    private val _titulos = MutableStateFlow<List<String>>(listOf())
    val titulos: StateFlow<List<String>> get() = _titulos

    private val _categorias = MutableStateFlow<List<String>>(listOf())
    val categorias: StateFlow<List<String>> get() = _categorias

    init{
        getTituloOptions()
        getCategoriasOptions()
    }

    fun getTituloOptions() {
        viewModelScope.launch{
            try{
                val result = optionsRepository.getTituloOptions()
                _titulos.emit(result.map { it -> it.asDomainModel().titulo })
                Log.d("GetOptionViewModelT", result.toString())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getCategoriasOptions(){
        viewModelScope.launch{
            try{
                val result = optionsRepository.getCategoriaOptions()
                _categorias.emit(result.map { it -> it.asDomainModel().categoria })
                Log.d("GetOptionViewModelC", result.toString())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun TituloDto.asDomainModel(): Titulo {
        return Titulo(
            tituloId = this.tituloId,
            titulo = this.titulo
        )
    }
    private fun CategoriaDto.asDomainModel(): Categoria {
        return Categoria(
            categoriaId = this.categoriaId,
            categoria = this.categoria
        )
    }
}