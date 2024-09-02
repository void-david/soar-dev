package com.example.todoapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.classes.CalendarDay
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agenda(navController: NavController){
    val listaDias = listOf(
        "D",
        "L",
        "M",
        "M",
        "J",
        "V",
        "S")
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Agenda")
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* User icon action */ }) {
                    Icon(Icons.Filled.Person, contentDescription = "User")
                }
            }
        )
    }){paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .background(Color.LightGray)
            .fillMaxSize()) {
            CalendarView(month = 10, year = 2024)
        }
    }
}

@Composable
fun CalendarView(month: Int, year: Int) {
    val days = getDaysInMonth(month, year)
    Column {
        // Display month and year header
        Text(text = "Month: $month, Year: $year")
        // Display days in a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7) // 7 columns for days of the week
        ) {
            items(days) { day ->
                Text(text = day.day.toString())
            }
        }
    }
}

fun getDaysInMonth(month: Int, year: Int): List<CalendarDay> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    return (1..daysInMonth).map { day ->
        CalendarDay(day, month,year)
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaPreview(){
    val navController = rememberNavController()
    Agenda(navController = navController)
}
