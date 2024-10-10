package com.example.todoapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
import com.example.todoapp.model.CaseRepository
import com.example.todoapp.viewmodel.CreateCaseViewModel
import com.example.todoapp.viewmodel.OptionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.format.Padding

@Composable
fun CreateCaseView(
    navController: NavController,
    createCaseViewModel: CreateCaseViewModel = hiltViewModel(),
    optionsViewModel: OptionsViewModel,
    paddingValues: PaddingValues
){
    var clienteId by remember { mutableStateOf<Int?>(null) }
    var loginError by remember { mutableStateOf(false) }
    var estado = "Abierto"
    val coroutineScope = rememberCoroutineScope()

    var createError by remember { mutableStateOf(false) }
    var emptyFields by remember { mutableStateOf(false) }

    var selectedTitle by remember { mutableStateOf(optionsViewModel.tituloOptions[0]) }
    var selectedCategory by remember { mutableStateOf(optionsViewModel.categoriaOptions[0]) }
    var selectedType by remember { mutableStateOf(optionsViewModel.tipoOptions[0]) }
    var clientName by remember { mutableStateOf("") }
    var nuc by remember { mutableStateOf("") }
    var supervisor by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var investigationUnit by remember { mutableStateOf("") }
    var unitLocation by remember { mutableStateOf("") }
    var fvAccess by remember { mutableStateOf("") }


    val titleOptions = optionsViewModel.tituloOptions
    val categoryOptions = optionsViewModel.categoriaOptions
    val typeOptions = optionsViewModel.tipoOptions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 32.dp,
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "Crear Caso",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Black)

        LazyColumn(
            modifier = Modifier
                .height(450.dp)
                .background(Color(0x99B69D74))

        ){
            item{
                FilterRowTextSelect(
                    text = "Tipo de cliente:",
                    selectedOption = selectedType,
                    onOptionSelected = {selectedType = it},
                    optionsList = typeOptions,
                    color = Color(0x00FFFFFF)
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                FilterRowTextSelect(
                    text = "Título: ",
                    selectedOption = selectedTitle,
                    onOptionSelected = {selectedTitle = it},
                    optionsList = titleOptions,
                    color = Color(0x00FFFFFF)
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                FilterRowTextSelect(
                    text = "Categoría: ",
                    selectedOption = selectedCategory,
                    onOptionSelected = {selectedCategory = it},
                    optionsList = categoryOptions,
                    color = Color(0x00FFFFFF)
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(text = "NUC:",
                    value = nuc,
                    placeholder = "Número único de causa",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {nuc = it},
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Nombre de cliente:",
                    value = clientName,
                    placeholder = "Nombre",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {clientName = it}
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Fiscal Titular:",
                    value = supervisor,
                    placeholder = "Nombre",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {supervisor = it}
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Contraseña FV:",
                    value = password,
                    placeholder = "Contraseña",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    onValueChange = {password = it}
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Unidad de Investigación:",
                    value = investigationUnit,
                    placeholder = "Unidad",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    onValueChange = {investigationUnit = it}
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Dirección de unidad:",
                    value = unitLocation,
                    placeholder = "Dirección",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    onValueChange = {unitLocation = it}
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                TextAndTextField(
                    text = "Acceso a Fiscalía Virtual:",
                    value = fvAccess,
                    placeholder = "Liga",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    onValueChange = {fvAccess = it}
                )
            }

        }

        HorizontalDivider(thickness = 1.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))

        if(emptyFields){
            Text(text = "Favor de llenar todos los campos", color = Color.Red)
            Spacer(modifier = Modifier.height(4.dp))
        }

        MenuButton(
            text = "CREAR CASO",
            onClick = {
                if(nuc != "" &&
                    clientName != "" &&
                    supervisor != "" &&
                    password != "" &&
                    investigationUnit != ""
                    && unitLocation != ""
                    && fvAccess != ""){
                    createCaseViewModel.createCase( // Cambiar variables a las de aqui
                        delito = selectedTitle,
                        estado = estado,
                        categoria = selectedCategory,
                        tipo = selectedType,
                        fecha = "",
                        nuc = nuc,
                        nombreCliente = clientName,
                        supervisor = supervisor,
                        password = password,
                        investigationUnit = investigationUnit,
                        unitLocation = unitLocation,
                        fvAccess = fvAccess
                    )
                    navController.navigate("dashboard")
                } else{
                    emptyFields = true

                }
            }
        )

    }
}

@Composable
fun TextAndTextField(
    text: String,
    value: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
){
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(Color(0x00FFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        FilterText(text = text)
        CustomTextField(
            placeholder = placeholder,
            value = value,
            onValueChange = { onValueChange(it) },
            keyboardOptions = keyboardOptions,
            isPassword = isPassword
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCasePreview(){
    val navController = rememberNavController()
    CreateCaseView(navController = navController,
        createCaseViewModel = createCaseViewModelMock(),
        optionsViewModel = OptionsViewModel(),
        paddingValues = PaddingValues(0.dp)
    )
}

fun createCaseViewModelMock(): CreateCaseViewModel {
    return CreateCaseViewModel(object : CaseRepository{
        override suspend fun getCasos(): List<CasoDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getCaso(id: Int): CasoDto {
            TODO("Not yet implemented")
        }

        override suspend fun getCasoEmpleadoByCaseId(id: Int): List<CasoEmpleadoDto> {
            TODO("Not yet implemented")
        }

        override suspend fun insertCaso(caso: Caso): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun updateCaso(
            casoId: Int,
            delito: String,
            estado: String,
            categoria: String,
            tipo: String,
            nuc: String,
            nombreCliente: String,
            supervisor: String,
            password: String,
            investigationUnit: String,
            unitLocation: String,
            fvAccess: String
        ) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteCaso(casoId: Int) {
            TODO("Not yet implemented")
        }

    })
}
