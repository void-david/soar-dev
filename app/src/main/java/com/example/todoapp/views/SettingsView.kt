package com.example.todoapp.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    navController: NavController,
    authViewModel: AuthViewModel
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MenuButton(text = "Cerrar Sesión", onClick = {
            if (context is Activity) {
                signOutAndRestart(context, authViewModel, scope)
            }
        })
    }

}

private fun signOutAndRestart(
    activity: Activity,
    authViewModel: AuthViewModel,
    scope: CoroutineScope
) {
    // Launch coroutine within the provided scope
    scope.launch {
        authViewModel.signOut()
        delay(1000)
        withContext(Dispatchers.Main) {
            val intent = activity.intent
            activity.finish()
            activity.startActivity(intent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    val navController = rememberNavController()
    SettingsView(
        navController = navController,
        authViewModel = authViewModelMock()
    )
}