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
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.CitasViewModel
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.reflect.typeOf


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
    val timeState = rememberTimePickerState()

    val username = authViewModel.username.collectAsState().value
    val userId = authViewModel.userId.collectAsState().value

    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC") // Or your preferred time zone
    val formatedDate = formatter.format(selectedDate)


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

            selectedDate?.let {
                Text("Selected Date: $it")
            }
            Text("Selected Date: $formatedDate")
        }
        item {
            TimePicker(state = timeState)



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