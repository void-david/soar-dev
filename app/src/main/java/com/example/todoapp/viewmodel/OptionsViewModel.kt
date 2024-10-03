package com.example.todoapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class OptionsViewModel : ViewModel() {
    var tituloOptions = mutableStateListOf(
        "",
        "Robo",
        "Fraude",
        "Violencia",
        "Terrorismo",
        "Título 5"
    )

    var categoriaOptions =
        listOf(
            "",
            "Abuso sexual",
            "Violencia",
            "Crimen Material",
            "Crimen Documento",
            "Categoría 5"
        )

    var estadoOptions = listOf(
        "",
        "Abierto",
        "Cerrado",
        "Pendiente",
        "Archivado",
        "Estado 5"
    )

    var tipoOptions = listOf(
        "Víctima",
        "Investigado"
    )
}