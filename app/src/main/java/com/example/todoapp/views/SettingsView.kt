package com.example.todoapp.views

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.viewmodel.AuthViewModel
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
    paddingValues: PaddingValues
){
    val context = LocalContext.current
    val intent = (context as Activity).intent
    var usuario by remember { mutableStateOf<UsuarioDto?>(null) }

    var nombre by remember { mutableStateOf(usuario?.name ?: "") }
    var apellido1 by remember { mutableStateOf(usuario?.lastName1 ?: "") }
    var apellido2 by remember { mutableStateOf(usuario?.lastName2 ?: "") }
    var correo by remember { mutableStateOf(usuario?.username ?: "") }
    var telefono by remember { mutableStateOf(usuario?.phone?.toString() ?: "") }
    var rol by remember { mutableStateOf(usuario?.role ?: "") }

    var updatedUser by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        usuario = authViewModel.getUsuarioById(authViewModel.userId.value)
        rol = authViewModel.role.value
        nombre = usuario?.name ?: ""
        apellido1 = usuario?.lastName1 ?: ""
        apellido2 = usuario?.lastName2 ?: ""
        correo = usuario?.username ?: ""
        telefono = usuario?.phone?.toString() ?: ""
        Log.d("SettingsView", "Usuario: $usuario")
        Log.d("SettingsView", "Rol: $rol")
        updatedUser = false
//        CHECAR SI ES ESTUDIANTE O NO, Y PERMITIR EDITAR LOS DATOS DE OPTIONSVIEWMODEL.KT")


    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(Color(0xFFFAFEFF)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                ) {
                item{
                    TextAndTextField(
                        text = "Nombre",
                        value = nombre,
                        placeholder = "Nombre",
                        onValueChange = {nombre = it},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                    )

                    HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    TextAndTextField(
                        text = "Primer apellido",
                        value = apellido1,
                        placeholder = "Apellido",
                        onValueChange = {apellido1 = it},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                    )

                    HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    TextAndTextField(
                        text = "Segundo apellido",
                        value = apellido2,
                        placeholder = "Apellido",
                        onValueChange = {apellido2 = it},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                    )

                    HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    TextAndTextField(
                        text = "Correo",
                        value = correo,
                        placeholder = "Correo",
                        onValueChange = {correo = it},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                    )

                    HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    TextAndTextField(
                        text = "Teléfono",
                        value = telefono,
                        placeholder = "Teléfono",
                        onValueChange = {telefono = it},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                    )
                }
            }
        }

        if(updatedUser){
            Text(text = "Usuario actualizado exitosamente!", color = Color(0xFF007000))
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        MenuButton(text = "Actualizar usuario",
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    authViewModel.updateUsuario(
                        UsuarioDto(
                            usuarioId = usuario?.usuarioId ?: 0,
                            username = correo,
                            password = usuario?.password ?: "",
                            phone = telefono.toLong(),
                            name = nombre,
                            lastName1 = apellido1,
                            lastName2 = apellido2,
                            role = rol
                        )
                    )
                    updatedUser = authViewModel.updatedUser
                }
            })

        Spacer(modifier = Modifier.height(16.dp))
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
        paddingValues = PaddingValues(0.dp)
    )
}