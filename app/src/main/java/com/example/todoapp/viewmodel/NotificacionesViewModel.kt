import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay



// Ejemplo de repositorio para ver si jala la cosa
data class Notificacion(
    val id: Int,
    val mensaje: String,
    val fechaActivacion: Long
)


class NotificacionesRepository {

    // Lista de notificaciones con fechas futuras y pasadas
    private val notificaciones = listOf(
        Notificacion(1, "Notificación 1", System.currentTimeMillis() - 10000), // Activada hace 10 segundos
        Notificacion(2, "Notificación 2", System.currentTimeMillis() + 20000), // Se activará en 20 segundos
        Notificacion(3, "Notificación 3", System.currentTimeMillis() + 30000), // Se activará en 30 segundos
        Notificacion(4, "Notificación 4", System.currentTimeMillis() - 20000)  // Activada hace 20 segundos
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
