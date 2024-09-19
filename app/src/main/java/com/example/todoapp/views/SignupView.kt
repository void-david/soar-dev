package com.example.todoapp.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignupView(navController: NavController){

}

@Preview
@Composable
fun SignupViewPreview(){
    val navController = rememberNavController()
    SignupView(navController = navController)
}