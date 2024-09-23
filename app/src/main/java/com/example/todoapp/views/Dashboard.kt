package com.example.todoapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
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

    var filterOption by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf("") }
    var selectedTitle by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedSort by remember { mutableStateOf("") }


    val listaTareas =
        listOf(
            "Caso 1",
            "Caso 2",
            "Caso 3",
            "Caso 4",
            "Caso 5"
        )

    val categoriaOptions =
        listOf(
            "Categoría 1",
            "Categoría 2",
            "Categoría 3",
            "Categoría 4",
            "Categoría 5"
        )

    val agruparOptions =
        listOf(
            "Agrupar 1",
            "Agrupar 2",
            "Agrupar 3",
            "Agrupar 4",
            "Agrupar 5"
        )

    val tituloOptions = listOf(
        "Título 1",
        "Título 2",
        "Título 3",
        "Título 4",
        "Título 5"
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

                        FilterRow2Texts(text = "Víctima",
                            text2 = "Investigado",
                            filterOption,
                            onFilterOptionSelected = {selectedOption -> filterOption = selectedOption})

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(
                            text = "Título de delito:",
                            selectedOption = selectedTitle,
                            onOptionSelected = {selectedTitle = it},
                            optionsList = tituloOptions)

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(
                            text = "Categoría de delito:",
                            selectedOption = selectedCategory,
                            onOptionSelected = {selectedCategory = it},
                            optionsList = categoriaOptions)

                        Spacer(modifier = Modifier.height(1.dp))
                        Spacer(modifier = Modifier.height(16.dp))

                        ModalTitleText(text = "Opciones de ordenado")

                        Spacer(modifier = Modifier.height(16.dp))
                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRow2Texts(text = "Fecha",
                            text2 = "Alfabéticamente",
                            sortOption,
                            onFilterOptionSelected = {selectedOption -> sortOption = selectedOption})

                        Spacer(modifier = Modifier.height(1.dp))

                        FilterRowTextSelect(
                            text = "Agrupar por:",
                            selectedOption = selectedSort,
                            onOptionSelected = {selectedSort = it},
                            optionsList = agruparOptions)

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

                        val scrollState = rememberScrollState()
                        var expanded by remember { mutableStateOf(false) }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.TopStart)
                            .background(Color.White)) {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Localized description"
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                scrollState = scrollState
                            ) {
                                repeat(30) {
                                    DropdownMenuItem(
                                        text = { Text("Item ${it + 1}") },
                                        onClick = { /* TODO */ },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Outlined.Edit,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
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
                    onClick = {navController.navigate("case_view")},
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFFFAFEFF)
                    )
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
fun FilterTextOptions(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
fun FilterSelect(options: List<String>,
                 selectedOption: String,
                 onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .wrapContentSize(Alignment.TopStart)
//        .background(Color.White)
//    ){
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            scrollState = scrollState,
//            offset = DpOffset(0.dp, 0.dp)
//        )
//        {
////            options.forEach { option ->
////                DropdownMenuItem(
////                    text = { Text(option) },
////                    onClick = {
////                        onOptionSelected(option)
////                        expanded = false
////                    }
////                )
////            }
//            repeat(2){
//                DropdownMenuItem(
//                    text = {
//                        Text("Option $it",
//                            color = Color.Black
//                            ) },
//                    onClick = {
//                        onOptionSelected("Option $it")
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .background(Color.White)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Localized description"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            scrollState = scrollState
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun FilterRow2Texts(text: String,
                    text2: String,
                    filterOption: String,
                    onFilterOptionSelected: (String) -> Unit) {
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3B55)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilterTextOptions(text, isSelected = filterOption == text, onClick = { onFilterOptionSelected(text) })
        FilterTextOptions(text2, isSelected = filterOption == text2, onClick = { onFilterOptionSelected(text2) })
    }
}

@Composable
fun FilterRowTextSelect(text: String,
                        selectedOption: String,
                        onOptionSelected: (String) -> Unit,
                        optionsList: List<String>){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D3B55)),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        FilterText(text)
        FilterSelect(
            options = optionsList,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected)
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
            focusedContainerColor = Color(0xFFF5FDFF),
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