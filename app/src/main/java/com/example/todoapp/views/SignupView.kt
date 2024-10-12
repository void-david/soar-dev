package com.example.todoapp.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.data.UsuarioDtoUpload
import io.github.jan.supabase.gotrue.providers.Azure.signUp
import kotlinx.coroutines.launch


@Composable
fun SignupView(navController: NavController, signUp: suspend (ClienteDtoUpload, UsuarioDtoUpload, String, String) -> Boolean) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Coroutine scope for suspend function

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
            modifier = Modifier
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Nombre input
        CustomTextField(
            placeholder = "Nombre",
            value = username,
            onValueChange = { newText -> username = newText }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Apellido(s) input
        CustomTextField(
            placeholder = "Apellido(s)",
            value = username,  // Should be a separate state for the last name
            onValueChange = { newText -> username = newText }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Correo electrónico input
        CustomTextField(
            placeholder = "Correo electrónico",
            value = email,
            onValueChange = { newText -> email = newText }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contraseña input
        CustomTextField(
            placeholder = "Contraseña",
            value = password,
            onValueChange = { newText -> password = newText },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(64.dp))

        if (loginError) {
            Text(text = "Invalid credentials", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // MenuButton for sign-up
        MenuButton(
            text = "CREAR USUARIO",
            onClick = {
                scope.launch {
                    val clienteDto = ClienteDtoUpload(
                        nombre = username,
                        apellido1 = "",
                        apellido2 = "",
                        ciudad = "",
                        sector = "",
                        calle = "",
                        numero = ""
                    )

                    val usuarioDto = UsuarioDtoUpload(
                        username = username,
                        password = password,
                        phone = 1234567890
                    )
                    Log.d("SignupView", "Username: $username")
                    val success = signUp(clienteDto, usuarioDto, email, password)
                    signUp(clienteDto, usuarioDto, email, password)
                    if (success) {
                        navController.navigate("dashboard")
                    } else {
                        loginError = true
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Navigate to login
        Row {
            Text(
                text = "¿Ya tienes una cuenta?",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.scale(1.2f)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = "Inicia sesión",
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier
                    .scale(1.2f)
                    .clickable { navController.navigate("login_view") }
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SignupViewPreview() {
    // Mock NavController
    val mockNavController = rememberNavController()

    // Mock signUp function
    val mockSignUp: suspend (ClienteDtoUpload, UsuarioDtoUpload, String, String) -> Boolean = { _, _, _, _ ->
        // Mocking a successful sign-up
        true
    }

    // Call the actual SignupView with the mock values
    SignupView(
        navController = mockNavController,
        signUp = mockSignUp
    )
}
