package com.example.todoapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.ui.theme.buttonColorMain
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Shape
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.viewmodel.UserViewModel
import io.github.jan.supabase.gotrue.SessionStatus
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport.Enabled

@Composable
fun UserAuthScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val sessionState by viewModel.sessionState.collectAsState()

    when (sessionState) {
        is SessionStatus.Authenticated -> navController.navigate("dashboard")
        SessionStatus.LoadingFromStorage -> LoadingScreen()
        SessionStatus.NetworkError -> ErrorScreen("Network error")
        is SessionStatus.NotAuthenticated -> LoginView(navController, viewModel)
    }
}

@Composable
fun LoginView(navController: NavHostController, viewModel: UserViewModel){
    val username = viewModel.email.collectAsState(initial = "")
    val password = viewModel.password.collectAsState()
    val loginError by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var imageLegal = painterResource(R.drawable.user_icon_on_transparent_background_free_png)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(painter = imageLegal, contentDescription = "Legal Hoy Logo",
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .padding(bottom = 25.dp)
        )

        CustomTextField(
            placeholder = "Correo",
            value = username.value,
            onValueChange = { viewModel.onEmailChange(it) } // Update username
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            placeholder = "Contraseña",
            value = password.value,
            onValueChange = { viewModel.onPasswordChange(it) }, // Update password
            isPassword = true)

        Spacer(modifier = Modifier.height(32.dp))

        if (loginError) {
            Text(text = "Invalid credentials", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        MenuButton(
            text = "INICIA SESIÓN",
            onClick = {
                viewModel.signIn()
            },
        )


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Olvidé mi contraseña",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(1.2f)
                .clickable { navController.navigate("signup_view") }

        )

        Spacer(modifier = Modifier.height(64.dp))

        MenuButton(
            text = "CREAR UNA USUARIO",
            onClick = { viewModel.signUp() }
        )
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: ButtonColors = ButtonDefaults.buttonColors(containerColor = buttonColorMain),
    textColor: Color = Color.White,
    textScale: Float = 1.5f) {
    Button(
        modifier = modifier
            .width(300.dp)
            .height(60.dp),
        colors = color,
        onClick = onClick,
        shape = shape
    ) {Text(
        text = text,
        modifier = Modifier.scale(textScale),
        color = textColor
    ) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    var textState by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .width(300.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left

        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                visualTransformation = if (isPasswordVisible || !isPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
            )
            if (isPassword) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Password visibility",
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .clickable { isPasswordVisible = !isPasswordVisible }
                    )
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Text(message)
}


@Composable
fun LoadingScreen() {
    CircularProgressIndicator()
}

@Composable
fun AuthenticatedScreen(viewModel: UserViewModel) {
    Column {
        Text("User is authenticated")
        Button(onClick = { viewModel.signOut() }) {
            Text("Sign Out")
        }
    }

}