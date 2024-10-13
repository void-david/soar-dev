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

@Composable
fun UnavailableCitaCard(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.padding(5.dp),
        // color should be light red
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFB69D74))
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = "No hay citas disponibles",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CitaCard(
    cita: Citas,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.padding(5.dp),
        onClick = { navController.navigate("agenda_case_view/${cita.id}") },
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFAFEFF))
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
                text = "Hora: ${cita.hora}:${cita.minuto.toString().padStart(2, '0')}",
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            val isEnabled = datePickerState.selectedDateMillis != null
            TextButton(
                onClick = {
                    if (isEnabled) {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    }
                },
                enabled = isEnabled
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agenda(navController: NavController,
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
            val allCitasAtHour = allCitasList.filter{ it.fecha == formatedDate }.filter { it.hora == hour }
            if (allCitasAtHour.isNotEmpty()) {
                Text(text = "Citas a las $hour:00")
                allCitasAtHour.forEach { cita ->
                    CitaCard(cita = cita, navController = navController)
                }

            }else {
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

