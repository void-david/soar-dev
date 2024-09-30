package com.example.todoapp.views

import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.viewmodel.UserViewModel
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.model.CaseRepository
import com.example.todoapp.model.UserRepository
import com.example.todoapp.viewmodel.CaseViewModel
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController,
              paddingValues: PaddingValues,
              caseViewModel: CaseViewModel = hiltViewModel(),
){
    var query by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }

    // Opciones de filtrado del modal
    var filterOption by remember { mutableStateOf("Víctima") }
    var sortOption by remember { mutableStateOf("Fecha") }

    var selectedTitle by remember { mutableStateOf("Título 1") }
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

        Text(text = "Filtrado: $filterOption",)
        Text(text = "Título: $selectedTitle")
        Text(text = "Categoría: $selectedCategory")

        Text(text = "Ordenado: $sortOption")
        Text(text = "Agrupado: $selectedSort")
        CaseListScreen(caseViewModel, navController, query)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(10.dp)
        ){
            items(1){
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

        if(showModal){
            Dialog(
                properties = androidx.compose.ui.window.DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false,

                    ),
                onDismissRequest = { showModal = false },
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(410.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                            onClick = { showModal = false},
                        ) {Text(
                            text = "Confirmar",
                            color = Color.Black,
                            modifier = Modifier.scale(1.5f)
                        ) }
                    }
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
        fontStyle = if (isSelected) FontStyle.Italic else FontStyle.Normal,
        textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,

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

    Box(modifier = Modifier
        .width(150.dp)
        .wrapContentSize(Alignment.TopStart)
        .background(Color.White)) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .width(120.dp)
                .height(25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                if(selectedOption != ""){
                    Text(
                        text = selectedOption,
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                }
                if(selectedOption == ""){
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            scrollState = scrollState,
            modifier = Modifier
                .background(Color.White)
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
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        FilterText(text)
        FilterSelect(
            options = optionsList,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
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

@Composable
fun CaseListScreen(viewModel: CaseViewModel, navController: NavController, query: String) {
    val casosList = viewModel.casos.collectAsState().value
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    if (loading) {
        CircularProgressIndicator()
    } else if (error.isNotEmpty()) {
        // Display an error message if needed
        Text(text = error, color = Color.Red)
    } else {
        val filteredCases = casosList.filter {
            it.delito.contains(query, ignoreCase = true)
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(filteredCases) { casoItem ->
                Log.d("CasoItem", casoItem.casoId.toString())
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.padding(5.dp),
                    onClick = { navController.navigate("case_view/${casoItem.casoId}") },
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFFFAFEFF)
                    )
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text(
                            text = "Delito: ${casoItem.delito}",
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Estado: ${casoItem.estado}",
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()

    // Provide mock actions or empty lambdas in place of the real ViewModel methods
    MaterialTheme {
        Dashboard(
            navController = navController,
            paddingValues = PaddingValues(0.dp),
            caseViewModel = caseViewModelMock()
        )
    }
}


// Mock or placeholder objects for preview
@Composable
fun userViewModelMock(): UserViewModel {
    return UserViewModel(object : UserRepository {
        // Mock session state as a loading state
        override val sessionState: StateFlow<SessionStatus> = MutableStateFlow(SessionStatus.LoadingFromStorage)

        // Provide other necessary methods
        override suspend fun signIn(userEmail: String, userPassword: String): Boolean {
            // Mock sign-in behavior
            return true
        }

        override suspend fun signUp(userEmail: String, userPassword: String): Boolean {
            // Mock sign-up behavior
            return true
        }

        override suspend fun signOut() {
            // Mock sign-out behavior
        }

        override suspend fun getEmpleado(): List<EmpleadoDto> {
            // Provide mock data for preview
            return listOf(
                EmpleadoDto(1, null, "Matricula 1", false, 1),
                EmpleadoDto(2, 1, "Matricula 2", true, 2)
            )
        }
    })
}


@Composable
fun caseViewModelMock(): CaseViewModel {
    return CaseViewModel(object : CaseRepository {
        // Mock methods and data for the CaseRepository

        override suspend fun getCasos(): List<CasoDto> {
            // Return mock data for preview
            return listOf(
                CasoDto(casoId = 1, delito = "Delito 1", estado = "Open", clienteId = 1),
                CasoDto(casoId = 2, delito = "Delito 2", estado = "Closed", clienteId = 2)
            )
        }

        override suspend fun getCaso(id: Int): CasoDto {
            // Mock single case by ID
            return CasoDto(casoId = 1, delito = "Delito 1", estado = "Open", clienteId = 1)
        }

        override suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto> {
            return listOf(
                CasoEmpleadoDto(casoEmpleadoId = 1, empleadoId = 1, casoId = 1),
                CasoEmpleadoDto(casoEmpleadoId = 2, empleadoId = 2, casoId = 2)
            )
        }

    })
}