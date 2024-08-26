package com.example.todoapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskView(navController: NavController, taskID: Int){
    var listaTareas =
        listOf(
            "Hacer comida",
            "Limpiar el baño",
            "Lavar los platos",
            "Hacer ejercicio",
            "Bañar al perro"
        )

    var listaDescripciones =
        listOf(
            "Cocinar el pescado a la Veracruzana, cocinar el arroz integral, preparar la limonada.",
            "Comprar el limpiador para baños, asegurarse de que el trapeador esté usable, limpiar regadera e inodoro.",
            "Lavar platos acumulados en el día.",
            "Ir al gimnasio a las 3:00 P.M., hacer pecho, espalda, y abdomen.",
            "Bañar al perro con el shampoo y jabón nuevos, asegurar de secarlo bien."
        )

        Column() {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier.padding(2.dp),
            ) {
                Text(text = "Tarea: ${listaTareas[taskID]}", color = Color.Black, modifier = Modifier.padding(10.dp))
                Text(text = listaDescripciones[taskID], color = Color.Black, modifier = Modifier
                    .background(Color.White)
                    .padding(26.dp))
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Center the content of the Box
            ) {
                Button(onClick = { navController.navigate("list_view") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription ="Back")
                }
            }
        }
}