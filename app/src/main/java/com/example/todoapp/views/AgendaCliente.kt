package com.example.todoapp.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.CitasViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.typeOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaCliente(navController: NavController,
           paddingValues: PaddingValues,
           citasViewModel: CitasViewModel = hiltViewModel(),
           authViewModel: AuthViewModel) {
    val dateState = rememberDatePickerState(System.currentTimeMillis())
    val timeState = rememberTimePickerState()
    val formatedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(dateState.selectedDateMillis)


    val username = authViewModel.username.collectAsState().value
    val userId = authViewModel.userId.collectAsState().value


    LaunchedEffect(Unit) {
        //UserId not fetching correctly, gives errors
        citasViewModel.getCitasByUserId(userId)
    }


    val citasList = citasViewModel.citasByUserId.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            Text(text = "Agenda")
            Text(text = "Username: ${username}")
            Text(text = "Id: ${userId}")
            Text(text = "Id: ${userId==3}")
        }

        item {
            DatePicker(state = dateState)
            TimePicker(state = timeState)

            Text(text = "Date: ${formatedDate}")
            Text(text = "Date in milis: ${dateState.selectedDateMillis} ")

            Text(text = "Hour: ${timeState.hour}")
            Text(text = "Minutes: ${timeState.minute}")

        }
        items(citasList) { cita ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.padding(5.dp),
                onClick = { navController.navigate("agenda_case_view/${cita.id}") },
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFFAFEFF)
                )
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text(
                        text = "Asunto: ${cita.asunto}",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Cliente: ${cita.clienteUsername}",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Id cliente: ${cita.clienteUserId}",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Fecha: ${cita.fecha}",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Hora: ${cita.hora}:${cita.minuto}",
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )


                }

            }
        }
        item{
            var asunto by remember { mutableStateOf("") }
            TextField(
                value = asunto,
                onValueChange = { asunto = it },
                label = { Text("asunto") }
            )

            Button(onClick = {
                citasViewModel.insertCita(
                    asunto = asunto,
                    hora = timeState.hour,
                    minuto = timeState.minute,
                    fecha = formatedDate,
                    clienteUsername = username,
                    clienteUserId = userId

                )
            }) {
                Text(text = "Crear cita")
            }

        }
    }

}