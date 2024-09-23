package com.example.todoapp.views

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

@Composable
fun SignupView(navController: NavController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    var imageLegal = painterResource(R.drawable.user_icon_on_transparent_background_free_png)

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

        CustomTextField(placeholder = "Nombre")

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(placeholder = "Apellido(s)")

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(placeholder = "Correo electrónico")

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeholder = "Contraseña",
            isPassword = true
        )


        Spacer(modifier = Modifier.height(64.dp))

        if (loginError) {
            Text(text = "Invalid credentials", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        MenuButton(
            text = "CREAR USUARIO",
            onClick = { navController.navigate("dashboard") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(){
            Text(
                text = "¿Ya tienes una cuenta?",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .scale(1.2f)
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
fun SignupViewPreview(){
    val navController = rememberNavController()
    SignupView(navController = navController)
}