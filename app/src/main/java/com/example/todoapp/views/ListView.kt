package com.example.todoapp.views

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ListView(navController: NavController){
    var listaTareas =
        listOf(
            "Hacer chambeadora",
            "Limpiar la chambeadora",
            "Lavar la chambeadora",
            "Hacer ejercicio en la chambeadora",
            "Bañar a la chambeadora"
        )

    var listaDescripciones =
        listOf(
            "Preparar el pescado y la cena",
            "Conseguir el limpiador",
            "Lavar platos acumulados en el dia",
            "Ir al gimnasio a las 3:00",
            "Usar el shampoo nuevo y la manguera"
        )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ){
        items(5){
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier.padding(2.dp),
                onClick = {navController.navigate("task_view/${it}")}
            ) {
                Text(text = "Tarea: ${listaTareas[it]}", color = Color.Black, modifier = Modifier.padding(10.dp))

                Text(text = listaDescripciones[it], color = Color.Black, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(26.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ListViewPreview(){
    val navController = rememberNavController()
    ListView(navController = navController)
}