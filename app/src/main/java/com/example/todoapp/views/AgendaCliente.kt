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
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.example.todoapp.data.Citas
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.CitasViewModel
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.reflect.typeOf


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaCliente(navController: NavController,
           paddingValues: PaddingValues,
           citasViewModel: CitasViewModel = hiltViewModel(),
           authViewModel: AuthViewModel) {

    val username = authViewModel.username.collectAsState().value
    val userId = authViewModel.userId.collectAsState().value


    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC") // Or your preferred time zone
    val formatedDate = formatter.format(selectedDate)


    LaunchedEffect(Unit) {
        citasViewModel.getCitas()
        citasViewModel.getCitasByUserId(userId)
    }



    val allCitasList = citasViewModel.citas.collectAsState().value
    val citasList = citasViewModel.citasByUserId.collectAsState().value
    val filteredCitasByDate = citasList.filter { it.fecha == formatedDate }
    val sortedCitasByHour = filteredCitasByDate.sortedBy { it.hora }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            if(citasList.isEmpty()){
                Text(text = "Aun no tienes citas")
            }else{
                Text(text = "Tienes citas para el dia ${citasList[0].fecha}")

            }
            Button(onClick = { showDialog = true }) {
                Text("Show Date Picker")
            }

            if (showDialog) {
                DatePickerModal(
                    onDateSelected = { date ->
                        selectedDate = date
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
            Text("Selected Date: $formatedDate")
        }
        items((10..16).toList()){ hour ->
            val citasAtHour = sortedCitasByHour.filter { it.hora == hour }
            val allCitasAtHour = allCitasList.filter{ it.fecha == formatedDate && it.hora == hour && !(it in citasAtHour)}
            if (citasAtHour.isNotEmpty()) {
                Text(text = "Citas a las $hour:00")
                citasAtHour.forEach { cita ->
                    CitaCard(cita = cita, navController = navController)
                }

            } else if (allCitasAtHour.isNotEmpty()){
                Text(text = "Citas a las $hour:00")
                allCitasAtHour.forEach { cita ->
                    UnavailableCitaCard()
                }

            }
            else {
                Text(text = "No hay citas para la hora $hour")
                var asunto by remember { mutableStateOf("") }
                TextField(
                    value = asunto,
                    onValueChange = { asunto = it },
                    label = { Text("asunto") }
                )
                Button(onClick = {

                    if(citasList.isEmpty()){
                        citasViewModel.insertCita(
                            asunto = asunto,
                            hora = hour,
                            minuto = 0,
                            fecha = formatedDate,
                            clienteUsername = username,
                            clienteUserId = userId

                        )
                    }else{
                        citasList[0].id?.let {
                            citasViewModel.updateCita(
                                citasId = it,
                                asunto = asunto,
                                hora = hour,
                                minuto = 0,
                                fecha = formatedDate,
                                clienteUsername = username,
                                clienteUserId = userId
                            )
                        }
                    }
                    navController.navigate("agenda_cliente")
                }) {
                    if(citasList.isEmpty()){
                        Text(text = "Crear cita")
                    }else{
                        Text(text = "Actualizar cita")

                    }
                }
            }

        }


    }

}