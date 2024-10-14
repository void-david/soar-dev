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
    var step by remember { mutableIntStateOf(1) } // State to track the current step
    val scope = rememberCoroutineScope() // Coroutine scope for suspend function

    var username by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var sector by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var addressNumber by remember { mutableStateOf("") }

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

        when (step) {
            1 -> Step1(
                username = username,
                lastName = lastName,
                email = email,
                password = password,
                phone = phone,
                onNext = { newUsername, newLastName, newEmail, newPassword, newPhone ->
                    // Update state with new values
                    username = newUsername
                    lastName = newLastName
                    email = newEmail
                    password = newPassword
                    phone = newPhone

                    // Validate inputs here
                    if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        // If valid, proceed to Step 2
                        step = 2
                    } else {
                        Log.d("SignupView", "Invalid inputs")
                        // Handle validation errors (e.g., show error messages)
                    }
                }
            )
            2 -> Step2(
                city = city,
                sector = sector,
                street = street,
                addressNumber = addressNumber,
                onSignUp = { newCity, newSector, newStreet, newAddressNumber ->
                    // Update state with new values
                    city = newCity
                    sector = newSector
                    street = newStreet
                    addressNumber = newAddressNumber

                    authViewModel.empleadoSignUp(
                        username = email,
                        password = password,
                        phone = phone.toLong(),
                        matricula = "",
                        estudiante = false,
                        jefeId = 0
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
){
    var localUsername by remember { mutableStateOf("") }
    var localLastName by remember { mutableStateOf("") }
    var localPassword by remember { mutableStateOf("") }
    var localEmail by remember { mutableStateOf("") }
    var localPhone by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    // Nombre input
    CustomTextField(
        placeholder = "Nombre",
        value = localUsername,
        onValueChange = { newText -> localUsername = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Apellido(s) input
    CustomTextField(
        placeholder = "Apellido(s)",
        value = localLastName,  // Should be a separate state for the last name
        onValueChange = { newText -> localLastName = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Correo electrónico input
    CustomTextField(
        placeholder = "Correo electrónico",
        value = localEmail,
        onValueChange = { newText -> localEmail = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Contraseña input
    CustomTextField(
        placeholder = "Contraseña",
        value = localPassword,
        onValueChange = { newText -> localPassword = newText },
        isPassword = true
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Telefono input
    CustomTextField(
        placeholder = "Telefono",
        value = localPhone,
        onValueChange = { newText -> localPhone = newText }
    )

    Spacer(modifier = Modifier.height(64.dp))

    if (loginError) {
        Text(text = "Invalid credentials", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
    }

    // MenuButton for sign-up
    MenuButton(
        text = "CONTINUAR",
        onClick = { onNext(localUsername, localLastName, localEmail, localPassword, localPhone) }
    )
}

@Composable
fun EmpleadoStep2(
    city: String,
    sector: String,
    street: String,
    addressNumber: String,
    onSignUp: (String, String, String, String) -> Unit
){
    var localCity by remember { mutableStateOf(city) }
    var localSector by remember { mutableStateOf(sector) }
    var localStreet by remember { mutableStateOf(street) }
    var localAddressNumber by remember { mutableStateOf(addressNumber) }
    var loginError by remember { mutableStateOf(false) }
    // Nombre input
    CustomTextField(
        placeholder = "Ciudad",
        value = localCity,
        onValueChange = { newText -> localCity = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Apellido(s) input
    CustomTextField(
        placeholder = "Sector",
        value = localSector,
        onValueChange = { newText -> localSector = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Correo electrónico input
    CustomTextField(
        placeholder = "Calle",
        value = localStreet,
        onValueChange = { newText -> localStreet = newText }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Contraseña input
    CustomTextField(
        placeholder = "Número Exterior",
        value = localAddressNumber,
        onValueChange = { newText -> localAddressNumber = newText },
    )

    Spacer(modifier = Modifier.height(64.dp))

    if (loginError) {
        Text(text = "Invalid credentials", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
    }

    // MenuButton for sign-up
    MenuButton(
        text = "CREAR USUARIO",
        onClick = { onSignUp(localCity, localSector, localStreet, localAddressNumber) }
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