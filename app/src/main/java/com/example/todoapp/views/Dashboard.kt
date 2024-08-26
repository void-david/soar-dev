package com.example.todoapp.views

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R


@Composable
fun Dashboard(navController: NavController){
    var listaTareas =
        listOf(
            "Robo de Sandwich",
            "Quién tapó el baño",
            "Comer un sandwich",
            "Chambear",
            "Manejar al trabajo"
        )

    Column (modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalAlignment = Alignment.End) {
            Icon(modifier = Modifier.size(35.dp), imageVector = Icons.Filled.AccountCircle, contentDescription = null)
        }
        Column {
            Text(text = "BIENVENID@", modifier = Modifier
                .padding(10.dp, top = 30.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Text(text = "LIC. ELMO JARRÓN", modifier = Modifier
                .padding(10.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp)

            Text(text = "Tienes 0 notificaciones", modifier = Modifier
                .padding(top = 0.dp, bottom = 10.dp, start = 10.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                )
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ElevatedCard(onClick = { /*TODO*/ }, elevation = CardDefaults.cardElevation(4.dp)) {
                        Icon(
                            modifier = Modifier
                                .size(120.dp),
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.Black,
                        )
                        Text(text = "Buscar Archivos", modifier = Modifier
                            .padding(bottom = 10.dp, top = 5.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier
                            .size(120.dp)
                            .border(BorderStroke(2.dp, Color.Black)),
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = Color.Black,
                    )
                    Text(text = "Revisar Agenda", modifier = Modifier
                        .padding(bottom = 10.dp, top = 5.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier
                            .size(120.dp)
                            .border(BorderStroke(2.dp, Color.Black)),
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = Color.Black,
                    )
                    Text(text = "Ver Sugerencias", modifier = Modifier
                        .padding(bottom = 10.dp, top = 5.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        HorizontalDivider(
            color = Color.Black,
            thickness = 2.dp
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp, bottom = 0.dp),
            text = "Recientes",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(10.dp)
        ){
            items(5){
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    modifier = Modifier
                        .padding(5.dp),
                    onClick = {navController.navigate("task_view/${it}")}
                ) {
                    Text(text = "Tarea: ${listaTareas[it]}",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(15.dp)
                            .fillParentMaxWidth()
                            .padding(start = 10.dp),

                        )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(){
    val navController = rememberNavController()
    Dashboard(navController = navController)
}
