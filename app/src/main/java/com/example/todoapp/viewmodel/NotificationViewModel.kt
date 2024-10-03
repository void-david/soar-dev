package com.example.todoapp.viewmodel

import android.app.Notification
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.NotificationDto
import com.example.todoapp.data.Notificacion
import com.example.todoapp.model.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
): ViewModel() {

    // StateFlow to hold the list of empleados
    private val _notifications = MutableStateFlow<List<Notificacion>>(listOf())
    open val notifications: StateFlow<List<Notificacion>> get() = _notifications

    // StateFlow to hold the list of empleados
    private val _notification = MutableStateFlow<Notificacion?>(null)
    val notification: MutableStateFlow<Notificacion?> get() = _notification

    suspend fun getNotifications() {
        val result = notificationRepository.getNotifications()
        _notifications.emit(result.map { it -> it.asDomainModel() })
    }

    suspend fun getNotification(id: Int) {
        val result = notificationRepository.getNotification(id)
        _notification.value = result.asDomainModel()
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getNotifications()
        }

    }
/*
    // Funcion que monitorea las fechas de activación
    fun monitorearNotificaciones() {
        viewModelScope.launch {
            while (true) {
                val ahora = System.currentTimeMillis()

                val notificacionesMostrables = mutableListOf<Notificacion>()
                for (notificacion in notifications) {
                    if (notificacion.fecha <= ahora) {
                        notificacionesMostrables.add(notificacion)
                    }
                }
                _notificaciones.value = notificacionesMostrables
                delay(1000) // Revisa cada segundo si hay nuevas notificaciones por mostrar
            }
        }
    }
*/
    // Mapping function from CasoDto to Caso
    private fun NotificationDto.asDomainModel(): Notificacion {
        return Notificacion(
            id = this.id,
            fecha = this.fecha,
            titulo = this.titulo,
            mensaje = this.mensaje,
        )
    }
}