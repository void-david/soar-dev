package com.example.todoapp.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.viewmodel.AuthViewModel

@Composable
fun EmpleadoSignupView(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var step by remember { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var jefeId by remember { mutableStateOf(0) }
    var street by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Registro",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.scale(3f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        when (step) {
            1 -> EmpleadoStep1(
                username = username,
                lastName = lastName,
                email = email,
                password = password,
                phone = phone,
                onNext = { newUsername, newLastName, newEmail, newPassword, newPhone ->
                    username = newUsername
                    lastName = newLastName
                    email = newEmail
                    password = newPassword
                    phone = newPhone

                    if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        step = 2
                    } else {
                        Log.d("SignupView", "Invalid inputs")
                    }
                }
            )
            2 -> EmpleadoStep2(
                matricula = matricula,
                jefeId = jefeId,
                street = street,
                onSignUp = { newMatricula, newJefeId, newStreet ->
                    matricula = newMatricula
                    jefeId = newJefeId
                    street = newStreet

                    authViewModel.empleadoSignUp(
                        username = email,
                        password = password,
                        phone = phone.toLong(),
                        matricula = matricula,
                        jefeId = jefeId,
                        nombre = username,
                        apellido1 = lastName.split(" ")[0],
                        apellido2 = lastName.split(" ").getOrElse(1) { "" },
                        admin = if (jefeId == 0) true else false,
                    )

                    navController.navigate("login_view")
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun EmpleadoStep1(
    username: String,
    lastName: String,
    email: String,
    password: String,
    phone: String,
    onNext: (String, String, String, String, String) -> Unit
) {
    var localUsername by remember { mutableStateOf(username) }
    var localLastName by remember { mutableStateOf(lastName) }
    var localEmail by remember { mutableStateOf(email) }
    var localPassword by remember { mutableStateOf(password) }
    var localPhone by remember { mutableStateOf(phone) }

    CustomTextField(
        placeholder = "Nombre",
        value = localUsername,
        onValueChange = { localUsername = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        placeholder = "Apellido(s)",
        value = localLastName,
        onValueChange = { localLastName = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        placeholder = "Correo electrónico",
        value = localEmail,
        onValueChange = { localEmail = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        placeholder = "Contraseña",
        value = localPassword,
        onValueChange = { localPassword = it },
        isPassword = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        placeholder = "Teléfono",
        value = localPhone,
        onValueChange = { localPhone = it }
    )

    Spacer(modifier = Modifier.height(64.dp))

    MenuButton(
        text = "CONTINUAR",
        onClick = { onNext(localUsername, localLastName, localEmail, localPassword, localPhone) }
    )
}

@Composable
fun EmpleadoStep2(
    matricula: String,
    jefeId: Int,
    street: String,
    onSignUp: (String, Int, String) -> Unit
) {
    var localMatricula by remember { mutableStateOf(matricula) }
    var localJefeId by remember { mutableStateOf(jefeId) }
    var localStreet by remember { mutableStateOf(street) }

    CustomTextField(
        placeholder = "Matrícula",
        value = localMatricula,
        onValueChange = { localMatricula = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

    CustomTextField(
        placeholder = "ID del Jefe",
        value = localJefeId.toString(),
        onValueChange = { localJefeId = it.toIntOrNull() ?: 0 }
    )

    Spacer(modifier = Modifier.height(64.dp))

    MenuButton(
        text = "CREAR USUARIO",
        onClick = { onSignUp(localMatricula, localJefeId, localStreet) }
    )
}

@Preview(showBackground = true)
@Composable
fun EmpleadoSignupViewPreview() {
    // Call the actual SignupView with the mock values
    val mockNavController = rememberNavController()
    SignupView(
        navController = mockNavController,
        authViewModel = authViewModelMock()
    )
}