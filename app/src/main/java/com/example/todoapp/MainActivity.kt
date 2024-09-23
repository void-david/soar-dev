package com.example.todoapp

import SearchEngine
import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.views.Agenda
import com.example.todoapp.views.CaseView
import com.example.todoapp.views.Dashboard
import com.example.todoapp.views.ListView
import com.example.todoapp.views.LoginView
import com.example.todoapp.views.InboxView
import com.example.todoapp.ui.theme.backgroundColor

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

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
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
                            if(currentDestination != "dashboard" && currentDestination != "agenda"){
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
                    var selectedItem by remember { mutableIntStateOf(0) }
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
                        LoginView(navController = navController)
                    }
                    composable("dashboard") {
                        Dashboard(navController = navController, paddingValues = innerPadding)
                    }
                    composable("case_view") {
                        CaseView(navController = navController, paddingValues = innerPadding)
                    }
                    composable("search_engine") {
                        SearchEngine(navController = navController, paddingValues = innerPadding)
                    }
                    composable("agenda") {
                        Agenda(navController = navController)
                    }
                    composable("inbox_view") {
                        InboxView(navController = navController, paddingValues = innerPadding)
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

