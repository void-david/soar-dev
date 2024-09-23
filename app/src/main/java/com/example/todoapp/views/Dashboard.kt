package com.example.todoapp.views

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController, paddingValues: PaddingValues){
    var query by remember { mutableStateOf("") }

        var listaTareas =
            listOf(
                "Caso 1",
                "Caso 2",
                "Caso 3",
                "Caso 4",
                "Caso 5"
            )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5EF))
                .padding(paddingValues)
        ) {
            SearchBar(
                query = query,
                onQueryChanged = { query = it },
                onClearQuery = { query = "" },
            )
            val imageSize: Int = 100
            ElevatedCard(
                modifier = Modifier
                    .padding(16.dp)
                    .width(258.dp)
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(Color(0xFFD5C8B1)),
                onClick = { navController.navigate("search_engine") }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = "Opciones de Filtrado",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(10.dp)
            ){
                items(5){
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        modifier = Modifier
                            .padding(5.dp),
                        onClick = {navController.navigate("case_view")}
                    ) {
                        Text(text = listaTareas[it],
                            color = Color.Black,
                            modifier = Modifier
                                .padding(15.dp)
                                .fillParentMaxWidth()
                                .padding(start = 20.dp),

                            )
                    }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    placeholderText: String = "Buscar archivo"
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = placeholderText) },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .padding(end = 30.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge
    )
}


@Preview(showBackground = true)
@Composable
fun DashboardPreview(){
    val navController = rememberNavController()
    Dashboard(navController = navController, paddingValues = PaddingValues())
}
