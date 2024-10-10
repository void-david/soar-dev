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
fun ResettingPasswordView(navController: NavController){
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var isntEqual by remember { mutableStateOf(false) }


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
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        CustomTextField(
            placeholder = "Nueva Contraseña",
            value = password,
            onValueChange = { newText ->  },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeholder = "Confirmar Contraseña",
            value = password2,
            onValueChange = { newText ->  },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "CAMBIAR",
            onClick = {
                if (password != password2) {
                    isntEqual = true
                }
                else {
                    isntEqual = false
                    // UPDATE PASSWORD EN TABLA?
                    navController.navigate("login_view")
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ResettingPasswordViewPreview(){
    val navController = rememberNavController()
    ResettingPasswordView(navController = navController)
}