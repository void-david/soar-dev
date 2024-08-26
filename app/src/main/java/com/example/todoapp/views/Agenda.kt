package com.example.todoapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agenda(navController: NavController){
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Dashboard")
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
            Text(text = "Agenda", modifier = Modifier
                .align(Alignment.CenterHorizontally),
                fontSize = 50.sp)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaPreview(){
    val navController = rememberNavController()
    Agenda(navController = navController)
}
