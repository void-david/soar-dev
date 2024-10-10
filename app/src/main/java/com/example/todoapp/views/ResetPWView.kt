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
fun ResetPWView(navController: NavController){
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
            text = "Recuperar Contraseña",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        CustomTextField(
            placeholder = "Correo electrónico",
            value = username,
            onValueChange = { newText ->  }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "CONTACTAR CORREO",
            onClick = { navController.navigate("resettingpassword_view") }
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
fun ResetPWViewPreview(){
    val navController = rememberNavController()
    ResetPWView(navController = navController)
}