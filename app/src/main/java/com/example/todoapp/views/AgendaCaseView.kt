package com.example.todoapp.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.CitasViewModel
import com.example.todoapp.viewmodel.ScheduleNotificationsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaCaseView(navController: NavController,
                   paddingValues: PaddingValues,
                   agendaCaseId: Int,
                   authViewModel: AuthViewModel,
                   citasViewModel: CitasViewModel = hiltViewModel(),
                   notificacionesViewModel: ScheduleNotificationsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        citasViewModel.getCita(agendaCaseId)
        citasViewModel.getCitas()
        citasViewModel.getCitasStatus(agendaCaseId)
    }
    val userRole = authViewModel.role.collectAsState().value
    val username = authViewModel.username.collectAsState().value
    val cita = citasViewModel.cita.collectAsState().value
    val allCitasList = citasViewModel.citas.collectAsState().value

    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }
    var newHour by remember { mutableStateOf(99) }

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC") // Or your preferred time zone
    val formatedDate = formatter.format(selectedDate)

    val citasStatus = citasViewModel.status.collectAsState().value


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botones de subir archivo y borrar
        item {
            Card(
                modifier = Modifier
                    .width(260.dp)
                    .height(120.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(Color(0xFFFAFEFF))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            citasViewModel.deleteCita(agendaCaseId)
                            if (userRole == "Empleado") {
                                navController.navigate("agenda")
                            } else {
                                navController.navigate("agenda_cliente")
                            }

                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        if(userRole == "Empleado") {
        item{
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(Color(0xFFFAFEFF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                if(citasStatus != null && citasStatus.status == "aceptada"){
                    Text(text = "   Cita aceptada", fontSize = 32.sp, color = Color(0xFF007000))
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2839)),
                        onClick = {
                            citasViewModel.updateStatus("pendiente", "", agendaCaseId)
                            navController.navigate("agenda_case_view/$agendaCaseId")
                        }) {
                        Text(text = "Poner cita en pendiente")
                    }

                }else{
                    Text(text = "   Cita pendiente", fontSize = 32.sp, color = Color(0xFFF47874))
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2839)),
                        onClick = {
                            citasViewModel.updateStatus("aceptada", username, agendaCaseId)
                            navController.navigate("agenda_case_view/$agendaCaseId")
                            CoroutineScope(Dispatchers.IO).launch{
                                notificacionesViewModel.scheduleNotifications(agendaCaseId)
                            }
                        }) {
                        Text(text = "Aceptar cita")
                    }
                }

            }

        }
    }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(Color(0xFFFAFEFF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                Text(
                    text = "(ID de la cita): ${agendaCaseId}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                if (citasStatus != null) {
                    Text(text = "    Status: ${citasStatus.status}",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 5.dp))
                    Text(text = "    Abogado responsable: ${citasStatus.abogadoResponsable}",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 5.dp))
                }

                Text(
                    text = "    Asunto: ${cita?.asunto}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "    Cliente: ${cita?.clienteUsername}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "    Fecha: ${cita?.fecha}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "    Hora: ${cita?.hora}:00",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )


                Spacer(
                    modifier = Modifier

                        .padding(top = 25.dp)
                )
            }
        }

        // Archivos del caso
        if(userRole == "Empleado") {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(800.dp)
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(Color(0xFFFAFEFF))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        item {
                            Text("Fecha seleccionada: $formatedDate")
                            Button(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(60.dp),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF1F2839
                                    )
                                ),
                                onClick = { showDialog = true }) {
                                Text("Mostrar calendario")
                                Icon(Icons.Default.DateRange, contentDescription = "DateRange")
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

                            Text("Horas disponibles para cambiar cita:")


                        }
                        val filteredCitas = allCitasList.filter { it.fecha == formatedDate }.sortedBy { it.hora }
                        items((10..16).toList()) { hour ->
                            if (!filteredCitas.any { it.hora == hour }) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(40.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF6A7DA0
                                        )
                                    ),
                                    onClick = {newHour = hour}) {
                                    Text("$hour:00")
                                }
                            }
                        }
                        item{
                            Text("Nueva fecha de la cita: $formatedDate")
                            Text("Nueva hora de la cita: $newHour:00")
                            Spacer(modifier = Modifier.height(10.dp))
                            if(newHour != 99) {
                                Button(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(60.dp),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF1F2839
                                        )
                                    ),
                                    onClick = {
                                        citasViewModel.updateCitaByTimeAndHour(
                                            formatedDate,
                                            newHour,
                                            agendaCaseId
                                        )

                                        navController.navigate("agenda")
                                    }) {
                                    Text("Cambiar fecha y hora de la cita")
                                }
                            }else{
                                Button(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(60.dp),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFF47874
                                        )
                                    ),
                                    onClick = {/*Do nothing*/ }) {
                                    Text("No se ha seleccionado hora")
                                }
                            }

                        }
                    }
                }
            }
        }

    }
}
