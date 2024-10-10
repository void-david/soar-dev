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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.viewmodel.UpdatePasswordViewModel

@Composable
fun ResettingPasswordView(
    navController: NavController,
    username: String,
    viewModel: UpdatePasswordViewModel = hiltViewModel()
){
    var password by remember { mutableStateOf("") }
    val password2 = viewModel.password.collectAsState()
    var isntEqual by remember { mutableStateOf(false) }
    var noPassword by remember { mutableStateOf(false) }
    Log.d("ResettingPassword", "Username: $username")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Recuperar Cuenta",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(2f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        CustomTextField(
            placeholder = "Nueva Contraseña",
            value = password,
            onValueChange = {
                noPassword = false
                password = it
                            },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeholder = "Confirmar Contraseña",
            value = password2.value,
            onValueChange = { viewModel.onPasswordChange(it) },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "CAMBIAR",
            onClick = {
                if (password == password2.value && password != ""){
                    isntEqual = false


                    // UPDATE PASSWORD EN TABLA?

                    viewModel.updatePassword(username = username, password = password2.value)
                    navController.navigate("login_view")
                } else if (password != password2.value) {
                    isntEqual = true
                } else
                    noPassword = true

            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        if (isntEqual){
            Text(text = "Las contraseñas no son iguales")
        } else if (noPassword) {
            Text(text = "Escriba la nueva contraseña")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResettingPasswordViewPreview(){
    val navController = rememberNavController()
    ResettingPasswordView(navController = navController, "sus")
}