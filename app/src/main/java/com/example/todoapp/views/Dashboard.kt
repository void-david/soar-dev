package com.example.todoapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.theme.buttonColorMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController, paddingValues: PaddingValues){
    var query by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(true) }

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
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = query,
            onQueryChanged = { query = it },
            onClearQuery = { query = "" },
        )

        MenuButton("Opciones de filtrado",
            onClick = { showModal = true },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            textScale = 1.5f
        )

        if(showModal){
            Dialog(
                onDismissRequest = { showModal = false },
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1F2839))
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ){
                        ModalTitleText(text = "Opciones de filtrado")

                        /* Cambiar Spacer de 1.dp por barra blanca del Figma */
                        Spacer(modifier = Modifier.height(16.dp))
                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRow2Texts(text = "Víctima", text2 = "Investigado")

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(text = "Título de delito:")

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(text = "Categoría de delito:")

                        Spacer(modifier = Modifier.height(1.dp))
                        Spacer(modifier = Modifier.height(16.dp))

                        ModalTitleText(text = "Opciones de ordenado")

                        Spacer(modifier = Modifier.height(16.dp))
                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRow2Texts(text = "Fecha", text2 = "Alfabéticamente")

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(text = "Agrupar por:")

                        Spacer(modifier = Modifier.height(1.dp))
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            modifier = Modifier
                                .width(200.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            onClick = { /* Agregar funcion para guardar variables */ },
                        ) {Text(
                            text = "Confirmar",
                            color = Color.Black,
                            modifier = Modifier.scale(1.5f)
                        ) }
                    }
                }
            }
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

@Composable
fun ModalTitleText(text: String){
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun FilterText(text: String){
    Text(
        text = text,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun FilterSelect(){

}

@Composable
fun FilterRow2Texts(text: String, text2: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3B55)),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        FilterText(text)
        FilterText(text2)
    }
}

@Composable
fun FilterRowTextSelect(text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3B55)),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        FilterText(text)
        FilterSelect()
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
            focusedContainerColor = Color(0xFFE6C693),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = Color.Black,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge,
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(){
    val navController = rememberNavController()
    Dashboard(navController = navController, paddingValues = PaddingValues())
}