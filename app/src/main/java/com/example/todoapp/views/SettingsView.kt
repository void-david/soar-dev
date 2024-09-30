package com.example.todoapp.views

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(navController: NavController){
    Text(text = "Settings")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsPreview() {
    SettingsView(navController = rememberNavController())
}