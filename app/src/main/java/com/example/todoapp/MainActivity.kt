package com.example.todoapp

import SearchEngine
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.UsuarioDtoUpload
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.backgroundColor
import com.example.todoapp.viewmodel.AuthViewModel
import com.example.todoapp.viewmodel.OptionsViewModel
import com.example.todoapp.views.Agenda
import com.example.todoapp.views.AgendaCaseView
import com.example.todoapp.views.AgendaCliente
import com.example.todoapp.views.CaseView
import com.example.todoapp.views.ClientFAQView
import com.example.todoapp.views.CreateCaseView
import com.example.todoapp.views.Dashboard
import com.example.todoapp.views.EmpleadoSignupView
import com.example.todoapp.views.InboxView
import com.example.todoapp.views.ResetPWView
import com.example.todoapp.views.ResettingPasswordView
import com.example.todoapp.views.SettingsView
import com.example.todoapp.views.SignupView
import com.example.todoapp.views.UpdateCaseView
import com.example.todoapp.views.UserAuthScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                TopAppBar()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    optionsViewModel: OptionsViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
){
    val navController = rememberNavController()
    MaterialTheme(
        colorScheme = lightColorScheme(background = backgroundColor)
    ) {
        Scaffold(
            topBar = {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

                // Condicionar para que no aparezca en el login
                if (currentDestination != "login_view" && currentDestination != "signup_view" && currentDestination != "resetpw_view" && currentDestination != "resettingpassword_view") {

                    val title = when (currentDestination) {
                        "login_view" -> "Login"
                        "signup_view" -> "Registro"
                        "dashboard" -> "Inicio"
                        "case_view" -> "Case 1"
                        "search_engine" -> "Buscar"
                        "agenda" -> "Agenda"
                        "inbox_view" -> "Inbox"
                        "settings" -> "Settings"
                        "agenda_cliente" -> "Mis citas"
                        "agenda_case_view/{agendaCaseId}" -> "Detalles de la cita"
                        "resetpw_view" -> "Forgot"
                        else -> "Task" // Default or specific for "task_view"
                    }

                    androidx.compose.material3.TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(title)
                            }
                        },
                        navigationIcon = {
                            if(currentDestination != "dashboard" && currentDestination != "agenda" && currentDestination != "inbox_view" && currentDestination != "settings"){
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                }
                            } else{
                                IconButton(onClick = {/* Rickroll XD */}) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "",
                                        modifier = Modifier.alpha(0f)
                                    )
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* User icon action */ }) {
                                Icon(Icons.Filled.Person, contentDescription = "User")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
                val userRole by authViewModel.role.collectAsState()
                if (currentDestination != "login_view" && currentDestination != "signup_view" && currentDestination != "resetpw_view" && currentDestination != "resettingpassword_view") {
                    var selectedItem by remember { mutableIntStateOf(0) }
                    val itemsList = listOf("Home", "Agenda", "Settings")
                    val iconsList = listOf(Icons.Filled.Home, Icons.Filled.DateRange, Icons.Filled.Settings)
                    NavigationBar(
                        containerColor = Color(0xFFB69D74)
                    ) {
                        itemsList.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(iconsList[index], contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    when (item) {
                                        "Settings" -> navController.navigate("settings")
                                        "Home" -> {
                                            when (userRole) {
                                                "Empleado" -> navController.navigate("dashboard")
                                                "Cliente" -> navController.navigate("client_FAQ")
                                            }
                                        }
                                        "Agenda" ->{
                                            when (userRole) {
                                                "Empleado" -> navController.navigate("agenda")
                                                "Cliente" -> navController.navigate("agenda_cliente")

                                            }
                                        }
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    indicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }
            },
            content = { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "login_view"
                ) {
                    composable("login_view") {
                        UserAuthScreen(navController = navController, viewModel = authViewModel)
                    }
                    composable("dashboard") {
                        Dashboard(navController = navController, paddingValues = innerPadding, optionsViewModel = optionsViewModel, authViewModel = authViewModel)
                    }
                    composable("case_view/{caseId}") { backStackEntry ->
                        val caseIdString = backStackEntry.arguments?.getString("caseId") // Parameter gets passed as string
                        val caseId = caseIdString?.toIntOrNull() // Convert to int
                        if (caseId != null) {
                            CaseView(navController = navController, paddingValues = innerPadding, caseId = caseId) // Pass caseId correctly
                        }
                    }
                    composable("search_engine") {
                        SearchEngine(navController = navController, paddingValues = innerPadding)
                    }
                    composable("agenda") {
                        Agenda(navController = navController, paddingValues = innerPadding, authViewModel = authViewModel)
                    }
                    composable("agenda_cliente"){
                        AgendaCliente(navController = navController, paddingValues = innerPadding, authViewModel = authViewModel)
                    }

                    composable("agenda_case_view/{agendaCaseId}"){ backStackEntry ->
                        val agendaCaseIdString = backStackEntry.arguments?.getString("agendaCaseId") // Parameter gets passed as string
                        val agendaCaseId = agendaCaseIdString?.toIntOrNull() // Convert to int
                        if (agendaCaseId != null) {
                            AgendaCaseView(navController = navController, paddingValues = innerPadding, agendaCaseId = agendaCaseId, authViewModel = authViewModel) // Pass caseId correctly
                        }
                    }

                    composable("inbox_view") {
                        InboxView(navController = navController, paddingValues = innerPadding)
                    }
                    composable("settings") {
                        SettingsView(navController = navController, authViewModel = authViewModel, paddingValues = innerPadding, optionsViewModel = optionsViewModel)
                    }
                    composable("create_case"){
                        CreateCaseView(navController = navController, optionsViewModel = optionsViewModel, paddingValues = innerPadding)
                    }
                    composable("update_case/{caseId}") { backStackEntry ->
                        val caseIdString = backStackEntry.arguments?.getString("caseId") // Parameter gets passed as string
                        val caseId = caseIdString?.toIntOrNull() // Convert to int
                        if (caseId != null) {
                            UpdateCaseView(navController = navController, paddingValues = innerPadding, caseId = caseId) // Pass caseId correctly
                        }
                    }
                    composable("client_FAQ") {
                        ClientFAQView(navController = navController, paddingValues = innerPadding)
                    }
                    composable("signup_view") {
                        SignupView(navController = navController, authViewModel = authViewModel)
                    }
                    composable("resetpw_view") {
                        ResetPWView(navController = navController)
                    }
                    composable("resettingpassword_view/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username") // Parameter gets passed as string
                        if (username != null) {
                            ResettingPasswordView(navController = navController, username = username)
                        }
                    }
                    composable("empleado_signup_view") {
                        EmpleadoSignupView(navController = navController, authViewModel = authViewModel)
                    }

//                composable(
//                    "task_view/{taskID}",
//                    arguments = listOf(navArgument("taskID") { type = NavType.IntType })
//                ) { backStackEntry ->
//                    val taskID = backStackEntry.arguments?.getInt("taskID")
//                    if (taskID != null) {
//                        TaskView(navController = navController, taskID = taskID)
//                    }
//                }
                }
            }
        )
    }
}

