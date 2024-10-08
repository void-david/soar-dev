package com.example.todoapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.CitasViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agenda(navController: NavController,
           paddingValues: PaddingValues,
           citasViewModel: CitasViewModel = hiltViewModel()) {
    val dateState = rememberDatePickerState(System.currentTimeMillis())
    val timeState = rememberTimePickerState()
    val formatedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(dateState.selectedDateMillis)

    LaunchedEffect(Unit) {
        citasViewModel.getCitas()
    }
    val citasList = citasViewModel.citas.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item {
            DatePicker(state = dateState)
            TimePicker(state = timeState)

            Text(text = "Date: ${formatedDate}")
            Text(text = "Date in milis: ${dateState.selectedDateMillis} ")

            Text(text = "Hour: ${timeState.hour}")
            Text(text = "Minutes: ${timeState.minute}")

        }
        items(citasList) { cita ->
            Text(text = cita.name)
            Text(text = cita.date)


        }
        item{
            var name by remember { mutableStateOf("") }
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )

            Button(onClick = {
                citasViewModel.insertCita(
                    name = name,
                    date = formatedDate
                )
            }) {
                Text(text = "Crear cita")
            }

        }
    }

}