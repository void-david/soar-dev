import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController

// Ejemplo de repositorio para ver si jala la cosa
data class Notificacion(
    val id: Int,
    val mensaje: String,
    val fechaActivacion: Long
)


class NotificacionesRepository {

    // Lista de notificaciones con fechas futuras y pasadas
    private val notificaciones = listOf(
        Notificacion(1, "Sanchezus", System.currentTimeMillis() - 10000), // Activada hace 10 segundos
        Notificacion(2, "Sera snacheado", System.currentTimeMillis() + 20000), // Se activará en 20 segundos
        Notificacion(3, "Aun no snapcha", System.currentTimeMillis() + 30000), // Se activará en 30 segundos
        Notificacion(4, "Snachon", System.currentTimeMillis() - 20000)  // Activada hace 20 segundos
    )

    // metodo para obtener todas las notificaciones
    fun obtenerNotificaciones(): List<Notificacion> {
        return notificaciones
    }
}


class NotificacionesViewModel(private val repository: NotificacionesRepository) : ViewModel() {

    // notificaciones que ya se deben mostrar
    val _notificaciones = MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    // Funcion que monitorea las fechas de activación
    fun monitorearNotificaciones() {
        viewModelScope.launch {
            while (true) {
                val ahora = System.currentTimeMillis()

                val notificacionesMostrables = mutableListOf<Notificacion>()
                for (notificacion in repository.obtenerNotificaciones()) {
                    if (notificacion.fechaActivacion <= ahora) {
                        notificacionesMostrables.add(notificacion)
                    }
                }
                _notificaciones.value = notificacionesMostrables
                delay(1000) // Revisa cada segundo si hay nuevas notificaciones por mostrar
            }
        }
    }
}



@Composable
fun Ejemplo(navController: NavController, viewModel: NotificacionesViewModel) {
    // Empieza a monitorear las notificaciones cuando el composable corre
    LaunchedEffect(Unit) {
        viewModel.monitorearNotificaciones()
    }

    // Recolecta la lista de notificaciones de ViewModel
    val notificaciones by viewModel.notificaciones.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Aqui se itera sobre las notificaciones y se despliega cada una
        items(notificaciones) { notificacion ->
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
                Text(
                    text = notificacion.mensaje,
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


@Preview(showBackground = true)
@Composable
fun PreviewEjemplo() {
    // Cosa rara de repositorio para desplegar la lista
    val repository = NotificacionesRepository()
    val viewModel = NotificacionesViewModel(repository)

    // Desplegar el ejemplo
    val navController = rememberNavController()
    Ejemplo(navController = navController, viewModel)
}