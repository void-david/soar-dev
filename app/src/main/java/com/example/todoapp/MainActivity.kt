package com.example.todoapp

import SearchEngine
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.backgroundColor
import com.example.todoapp.viewmodel.GetCaseViewModel
import com.example.todoapp.viewmodel.OptionsViewModel
import com.example.todoapp.viewmodel.UserViewModel
import com.example.todoapp.views.Agenda
import com.example.todoapp.views.AgendaCaseView
import com.example.todoapp.views.CaseView
import com.example.todoapp.views.CreateCaseView
import com.example.todoapp.views.Dashboard
import com.example.todoapp.views.InboxView
import com.example.todoapp.views.SettingsView
import com.example.todoapp.views.UpdateCaseView
import com.example.todoapp.views.UserAuthScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(optionsViewModel: OptionsViewModel = hiltViewModel()){
    val navController = rememberNavController()
    MaterialTheme(
        colorScheme = lightColorScheme(background = backgroundColor)
    ) {
        Scaffold(
            topBar = {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

                // Condicionar para que no aparezca en el login
                if (currentDestination != "login_view" && currentDestination != "signup_view") {

                    val title = when (currentDestination) {
                        "login_view" -> "Login"
                        "dashboard" -> "Inicio"
                        "case_view" -> "Case 1"
                        "search_engine" -> "Buscar"
                        "agenda" -> "Agenda"
                        "inbox_view" -> "Inbox"
                        "settings" -> "Settings"
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
                if (currentDestination != "login_view" && currentDestination != "signup_view") {
                    var selectedItem by remember { mutableIntStateOf(1) }
                    val itemsList = listOf("Settings", "Home", "Inbox", "Agenda")
                    val iconsList = listOf(Icons.Filled.Settings, Icons.Filled.Home, Icons.Filled.Email, Icons.Filled.DateRange)
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
                                        "Home" -> navController.navigate("dashboard")
                                        "Inbox" -> navController.navigate("inbox_view")
                                        "Agenda" -> navController.navigate("agenda")
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
                        UserAuthScreen(navController = navController)
                    }
                    composable("dashboard") {
                        Dashboard(navController = navController, paddingValues = innerPadding, optionsViewModel = optionsViewModel)
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
                        Agenda(navController = navController, paddingValues = innerPadding)
                    }
                    composable("agenda_case_view/{agendaCaseId}"){ backStackEntry ->
                        val agendaCaseIdString = backStackEntry.arguments?.getString("agendaCaseId") // Parameter gets passed as string
                        val agendaCaseId = agendaCaseIdString?.toIntOrNull() // Convert to int
                        if (agendaCaseId != null) {
                            AgendaCaseView(navController = navController, paddingValues = innerPadding, agendaCaseId = agendaCaseId) // Pass caseId correctly
                        }
                    }

                    composable("inbox_view") {
                        InboxView(navController = navController, paddingValues = innerPadding)
                    }
                    composable("settings") {
                        SettingsView(navController = navController)
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

