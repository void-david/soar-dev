package com.example.todoapp

import SearchEngine
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.views.Agenda
import com.example.todoapp.views.CaseView
import com.example.todoapp.views.Dashboard
import com.example.todoapp.views.ListView
import com.example.todoapp.views.LoginView
import com.example.todoapp.views.TaskView

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
    Scaffold(
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login_view"){
                composable("login_view"){
                    LoginView(navController = navController)
                }
                composable("dashboard"){
                    Dashboard(navController = navController)
                }
                composable("case_view"){
                    CaseView(navController = navController)
                }
                composable("search_engine"){
                    SearchEngine(navController = navController)
                }
                composable("agenda"){
                    Agenda(navController = navController)
                }
                composable(
                    "task_view/{taskID}",
                    arguments = listOf(navArgument("taskID") { type = NavType.IntType })) {
                        backStackEntry ->
                    val taskID = backStackEntry.arguments?.getInt("taskID")
                    if (taskID != null) {
                        TaskView(navController = navController, taskID = taskID)
                    }
                }
            }
        }
    )
}

