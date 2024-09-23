 package com.example.todoapp.views

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseView(navController: NavHostController, paddingValues: PaddingValues) {
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
                        onClick = { /* Upload action */ },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Upload",
                            modifier = Modifier
                                .fillMaxSize(),
                        )
                    }
                    IconButton(
                        onClick = { /* Delete action */ },
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
                Spacer(modifier = Modifier
                    .padding(top = 20.dp)
                )
                Text(
                    text = "Estado: En Progreso",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier
                    .padding(top = 10.dp)
                )

                Text(
                    text = "  Ultima modificacion: ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "  Creado: ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "  Titular: ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "  Alumnos: ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )

                Spacer(modifier = Modifier

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
                    items(listOf(
                        "PDF 1 - NOMBRE - FECHA",
                        "PDF 2 - NOMBRE - FECHA",
                        "PDF 3 - NOMBRE - FECHA",
                        "PDF 4 - NOMBRE - FECHA",
                        "PDF 5 - NOMBRE - FECHA"
                    )) { file ->
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

 @Preview(showBackground = true)
 @Composable
 fun CaseViewPreview(){
     val navController = rememberNavController()
     CaseView(navController = navController, paddingValues = PaddingValues(32.dp))
 }