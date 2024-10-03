package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.model.CitasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetCitasViewModel @Inject constructor(
    private val citasRepository: CitasRepository
): ViewModel() {
    fun getCitas() {


    }
}