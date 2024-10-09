package com.example.todoapp.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
import com.example.todoapp.model.CaseRepository
import com.example.todoapp.viewmodel.GetCaseViewModel
import com.example.todoapp.viewmodel.OptionsViewModel
import com.example.todoapp.viewmodel.UpdateCaseViewModel

@Composable
fun UpdateCaseView(
    navController: NavController,
    paddingValues: PaddingValues,
    caseId: Int,
    updateCaseViewModel: UpdateCaseViewModel = hiltViewModel(),
    getCaseViewModel: GetCaseViewModel = hiltViewModel(),
    optionsViewModel: OptionsViewModel = hiltViewModel()
){
    val caso by getCaseViewModel.caso.collectAsState()
    var delito by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var emptyFields by remember { mutableStateOf(false) }

    var selectedTitle by remember { mutableStateOf(caso?.delito ?: "") }
    var selectedCategory by remember { mutableStateOf(caso?.categoria ?: "") }
    var selectedType by remember { mutableStateOf(caso?.tipo ?: "") }
    var clientName by remember { mutableStateOf(caso?.nombreCliente ?: "") }
    var nuc by remember { mutableStateOf(caso?.nuc ?: "") }
    var supervisor by remember { mutableStateOf(caso?.supervisor ?: "") }
    var password by remember { mutableStateOf(caso?.password ?: "") }
    var investigationUnit by remember { mutableStateOf(caso?.investigationUnit ?: "") }
    var unitLocation by remember { mutableStateOf(caso?.unitLocation ?: "") }
    var fvAccess by remember { mutableStateOf(caso?.fvAccess ?: "") }
    var estado by remember { mutableStateOf(caso?.estado ?: "") }

    val titleOptions = optionsViewModel.tituloOptions
    val categoryOptions = optionsViewModel.categoriaOptions
    val typeOptions = optionsViewModel.tipoOptions

    LaunchedEffect(key1 = caseId) {
        getCaseViewModel.getCaso(caseId)
    }

    LaunchedEffect(key1 = caso){
        if (caso != null) {
            Log.d("UpdateCaseView", "Caso: $caso")
            // Initialize your state variables here
            selectedTitle = caso!!.delito
            estado = caso!!.estado
            selectedCategory = caso!!.categoria
            selectedType = caso!!.tipo
            clientName = caso!!.nombreCliente
            nuc = caso!!.nuc
            supervisor = caso!!.supervisor
            password = caso!!.password
            investigationUnit = caso!!.investigationUnit
            unitLocation = caso!!.unitLocation
            fvAccess = caso!!.fvAccess
        }
    }

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
    ) {
        Text(
            text = "Actualizar Caso",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(8.dp))

//        Text(text = "Caso #$caseId")
//        Text(text = "Estado: $estado")
//        Text(text = "Delito: $selectedTitle")
//        Text(text = "Cliente: $clientName")
//        Text(text = "Categoría: $selectedCategory")
//        Text(text = "Tipo: $selectedType")
//        Text(text = "NUC: $nuc")
//        Text(text = "Supervisor: $supervisor")
//        Text(text = "Contraseña: $password")
//        Text(text = "Unidad investigacion: $investigationUnit")
//        Text(text = "Dirección unidad: $unitLocation")
//        Text(text = "Acceso FV: $fvAccess")
        
        Spacer(modifier = Modifier.height(64.dp))

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
                    text = "Título:",
                    selectedOption = selectedTitle,
                    onOptionSelected = {selectedTitle = it},
                    optionsList = titleOptions,
                    color = Color(0x00FFFFFF)
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Black)
                FilterRowTextSelect(
                    text = "Categoría:",
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
            text = "ACTUALIZAR CASO",
            onClick = { updateCaseViewModel.updateCase(
                casoId = caseId,
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
            ) }
        )

        Spacer(modifier = Modifier.height(64.dp))
    }
}

fun updateCaseViewModelMock(): UpdateCaseViewModel {
    return UpdateCaseViewModel(object: CaseRepository {
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
            fecha: String,
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

@Preview(showBackground = true)
@Composable
fun UpdateCasePreview(){
    val navController = rememberNavController()
    UpdateCaseView(navController = navController,
        getCaseViewModel = caseViewModelMock(),
        updateCaseViewModel = updateCaseViewModelMock(),
        caseId = 99,
        paddingValues = PaddingValues(),
        optionsViewModel = OptionsViewModel()
    )
}
