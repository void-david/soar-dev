package com.example.todoapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.Caso
import com.example.todoapp.data.CasoDto
import com.example.todoapp.data.CasoEmpleadoDto
import com.example.todoapp.model.CaseRepository
import com.example.todoapp.viewmodel.GetCaseViewModel
import com.example.todoapp.viewmodel.UpdateCaseViewModel

@Composable
fun UpdateCaseView(
    navController: NavController,
    paddingValues: PaddingValues,
    caseId: Int,
    updateCaseViewModel: UpdateCaseViewModel = hiltViewModel(),
    getCaseViewModel: GetCaseViewModel = hiltViewModel(),
){
    val caso by getCaseViewModel.caso.collectAsState()
    var delito by remember { mutableStateOf("") }
    var clienteId by remember { mutableStateOf<Int?>(null) }
    var loginError by remember { mutableStateOf(false) }
    var estado = "Abierto"
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(caso) {
        getCaseViewModel.getCaso(caseId)
        delito = caso?.delito ?: ""
        clienteId = caso?.clienteId
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Actualizar Caso",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .scale(3f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        CustomTextField(
            placeholder = "Delito",
            value = delito,
            onValueChange = { delito = it } // Update username
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Luego hago un menú para seleccionar al cliente
        CustomTextField(
            placeholder = "Cliente ID",
            value = clienteId?.toString() ?: "",
            onValueChange = { clienteId = it.toInt() }
        )


        Spacer(modifier = Modifier.height(64.dp))

        if (loginError) {
            Text(text = "Invalid credentials", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        MenuButton(
            text = "ACTUALIZAR CASO",
            onClick = {
                clienteId?.let {
                    updateCaseViewModel.updateCase(caseId, delito, estado, it)
                    navController.navigate("dashboard")
                }
            }
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
            clienteId: Int
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
        caseId = 1,
        paddingValues = PaddingValues()
    )
}
