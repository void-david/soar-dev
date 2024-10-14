package com.example.todoapp.views

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.model.UserRepositoryImpl
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    navController: NavController,
    authViewModel: AuthViewModel,
){
    val context = LocalContext.current
    val intent = (context as Activity).intent
    var usuario by remember { mutableStateOf<UsuarioDto?>(null) }

    LaunchedEffect(Unit) {
        usuario = authViewModel.getUsuarioById(authViewModel.userId.value)
        Log.d("SettingsView", "Usuario: $usuario")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nombre: $usuario.nombre"
        )

        MenuButton(text = "Cerrar Sesión", onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                authViewModel.signOut()
                delay(1000)
                withContext(Dispatchers.Main) {
                    context.finish()
                    context.startActivity(intent)
                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsView(
        navController = rememberNavController(),
        authViewModel = authViewModelMock(),
    )
}