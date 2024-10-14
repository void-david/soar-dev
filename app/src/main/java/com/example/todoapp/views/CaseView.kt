 package com.example.todoapp.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todoapp.data.Caso
import com.example.todoapp.viewmodel.DeleteCaseViewModel
import com.example.todoapp.viewmodel.GetCaseViewModel
import com.example.todoapp.viewmodel.UserViewModel
import kotlinx.atomicfu.TraceBase.None.append


 @Composable
fun CaseView(navController: NavHostController,
             paddingValues: PaddingValues,
             caseId: Int,
             getCaseViewModel: GetCaseViewModel = hiltViewModel(),
             userViewModel: UserViewModel = hiltViewModel(),
             deleteCaseViewModel: DeleteCaseViewModel = hiltViewModel()
) {

     val caso by getCaseViewModel.caso.collectAsState()
     val empleados by userViewModel.empleados.collectAsState()
     val assignedEmployees = getCaseViewModel.assignedEmpleados.collectAsState()
     val filteredEmployees = empleados.filter { empleado -> empleado.empleadoId in assignedEmployees.value.map { it?.empleadoId } }
     val titular = filteredEmployees.find { titular -> titular.jefeId == null }
     val alumnos = filteredEmployees.filter { empleado -> empleado.jefeId == titular?.empleadoId }

     Log.d("CaseView", "Assigned Employees: ${listOf(filteredEmployees)}")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5EF))
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botones de subir archivo y borrar
        Card(
            modifier = Modifier
                .width(260.dp)
                .height(120.dp)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(Color(0xFFFAFEFF))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.navigate("update_case/$caseId") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
                IconButton(
                    onClick = {
                        deleteCaseViewModel.deleteCase(caseId)
                        navController.navigate("dashboard")
                              },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(Color(0xFFFAFEFF)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),

            ){

                Text(
                    text = "Estado: ${caso?.estado}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier
                    .padding(top = 10.dp)
                )

                Text(
                    text = "NUC: ${caso?.nuc}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Título: ${caso?.delito}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                //            Text(
                //                text = "  Categoría: ${caso?.categoria}",
                //                fontSize = 14.sp,
                //                modifier = Modifier
                //                    .padding(top = 5.dp)
                //            )
                Text(
                    text = "Tipo: ${caso?.tipo}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Fecha de creación: ${caso?.fecha}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Última modificación: ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Titular: ${caso?.supervisor}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Nombre del cliente: ${caso?.nombreCliente}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Alumnos: ${alumnos.joinToString(", ") { it.matricula }}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Unidad de investigación: ${caso?.investigationUnit}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "Ubicación de la unidad: ${caso?.unitLocation}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                ClickableTextCase(caso = caso)
                Text(
                    text = "Contraseña del caso: ${caso?.password}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )

                Spacer(modifier = Modifier

                    .padding(top = 25.dp)
                )
            }

        }


        LaunchedEffect(Unit) {
            getCaseViewModel.getCaso(caseId)
            getCaseViewModel.getCasoEmpleadoByCaseId(caseId)
            userViewModel.getEmpleado()
        }
    }
}

 @Composable
 fun ClickableTextCase(
     caso: Caso?
 ){
     val context = LocalContext.current
     val annotatedString = buildAnnotatedString {
         append("Acceso a la Fiscalía Virtual: ")
         pushStringAnnotation(
             tag = "URL",
             annotation = caso?.fvAccess ?: "" // Provide URL here
         )
         withStyle(
             style = SpanStyle(
                 color = Color.Blue,
                 textDecoration = TextDecoration.Underline
             )
         ) {
             append(caso?.fvAccess ?: "") // Display the URL
         }
         pop()
     }

     ClickableText(text = annotatedString,
         onClick = { offset ->
             annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                 .firstOrNull()?.let { annotation ->
                     // Get activity context
                     val activity = context as? Activity ?: return@let

                     // Add scheme if missing
                     val url = if (annotation.item.startsWith("http://") || annotation.item.startsWith("https://")) {
                         annotation.item
                     } else {
                         "https://" + annotation.item
                     }

                     // Open the URL in a browser
                     val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                     activity.startActivity(intent)
                 }
         }
     )
 }

 @Preview(showBackground = true)
 @Composable
 fun CaseViewPreview(){
     val navController = rememberNavController()
     CaseView(navController = navController, paddingValues = PaddingValues(32.dp),
         getCaseViewModel = caseViewModelMock(), userViewModel = userViewModelMock(), caseId = 1,
     )
 }