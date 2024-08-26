package com.example.testing_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testing_navigation.ui.theme.Testing_navigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Testing_navigationTheme {
                PreviewView()
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val n:Int = 5
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen / Tareas", fontSize = 24.sp)
        for(i in 1..n){
        Card(
            onClick = {navController.navigate("detail_screen")},
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .size(width = 400.dp, height = 80.dp)
        ) {
            Text(text = "Tarea: Trabajar",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
            Text(text = "Descripcion: Trabajo ligero...",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
            )

        }
        }


    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom){
        FloatingActionButton(
        onClick = {
        },
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 70.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
    }

}

@Composable
fun DetailScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Detail Screen", fontSize = 24.sp)
        Card(

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .size(width = 400.dp, height = 400.dp)
        ) {
            Text(text = "Tarea: Trabajar",
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                textAlign = TextAlign.Center,
            )
            Text(text = "Descripcion: Trabajo ligero...\n\n" +
                "Te la creiste, aqui no hacemos eso\n" +
                "Somos estudiantes de universidad\n" +
                "La chamba es eterna, y retadora.",
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(horizontal = 36.dp),
                textAlign = TextAlign.Start,
            )

        }
        Button(onClick = {
            navController.navigate("home_screen")
        }) {
            Text("Back to Home")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen")
    {
        composable("home_screen") {
            HomeScreen(navController = navController)
        }
        composable("detail_screen") {
            DetailScreen(navController = navController)
        }
    }
}