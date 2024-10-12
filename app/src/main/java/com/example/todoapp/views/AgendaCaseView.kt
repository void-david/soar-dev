package com.example.todoapp.views

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.CitasViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaCaseView(navController: NavController,
                   paddingValues: PaddingValues,
                   agendaCaseId: Int,
                    citasViewModel: CitasViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        citasViewModel.getCita(agendaCaseId)
    }

    val cita = citasViewModel.cita.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botones de subir archivo y borrar
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
                IconButton(
                    onClick = {
                        citasViewModel.deleteCita(agendaCaseId)
                        navController.navigate("Agenda")

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

            Text(
                text = "Asunto: ${cita?.asunto}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = "Cliente: ${cita?.clienteUsername}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = "Fecha: ${cita?.fecha}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = "Hora: ${cita?.hora}:${cita?.minuto}",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
            )


            Spacer(
                modifier = Modifier

                    .padding(top = 25.dp)
            )
        }

        // Archivos del caso
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(Color(0xFFFAFEFF))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(
                    listOf(
                        "PDF 1 - NOMBRE - FECHA",
                        "PDF 2 - NOMBRE - FECHA",
                        "PDF 3 - NOMBRE - FECHA",
                        "PDF 4 - NOMBRE - FECHA",
                        "PDF 5 - NOMBRE - FECHA"
                    )
                ) { file ->
                    Column {
                        Text(
                            text = file,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(10.dp)
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

