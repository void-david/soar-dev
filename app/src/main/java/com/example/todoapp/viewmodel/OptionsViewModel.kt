package com.example.todoapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.OptionsRepository
import javax.inject.Inject

class OptionsViewModel ( ){

    var estadoOptions = mutableStateListOf(
        "",
        "Abierto",
        "Cerrado",
        "Pendiente",
        "Archivado",
    )

    var tipoOptions = listOf(
        "Víctima",
        "Investigado",
        "Otro"
    )

    var filterOption = ""
    var sortOption = ""
    var orderOption = "Ascendente"

    var selectedTitle = ""
    var selectedCategory = ""
    var selectedState = ""
}