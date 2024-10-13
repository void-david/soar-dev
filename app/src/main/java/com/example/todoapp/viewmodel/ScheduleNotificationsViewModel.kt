package com.example.todoapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.todoapp.classes.NotificationHandler
import com.example.todoapp.data.Notificacion
import com.example.todoapp.data.NotificationDto
import com.example.todoapp.model.NotificationRepository
import com.example.todoapp.model.UserRepository
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaLocalDateTime
import javax.inject.Inject

class ScheduleNotificationsViewModel @Inject constructor (
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
    private val notificationHandler: NotificationHandler,
    private val auth: Auth
): ViewModel() {

    // StateFlow to hold the list of empleados
    private val _notifications = MutableStateFlow<List<Notificacion>>(listOf())
    open val notifications: StateFlow<List<Notificacion>> get() = _notifications

    // StateFlow to hold the list of empleados
    private val _notification = MutableStateFlow<Notificacion?>(null)
    val notification: MutableStateFlow<Notificacion?> get() = _notification

    val userId = userRepository.userId

    suspend fun getNotifications() {
        val result = notificationRepository.getNotifications()
        _notifications.emit(result.map { it -> it.asDomainModel() })
    }

    suspend fun getNotification(id: Int) {
        val result = notificationRepository.getNotification(id)
        _notification.value = result.asDomainModel()
    }

    suspend fun getNotificationsByUserId(userId: Int) {
        val result = notificationRepository.getNotificationsByUserId(userId)
        _notifications.emit(result.map { it -> it.asDomainModel() })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun scheduleNotifications() {
        var email = ""
        try {
            withContext(Dispatchers.Main){
                auth.currentUserOrNull()?.email?.let { email = it }
                userRepository.checkUserId(email)
            }
            val notifications = notificationRepository.getNotificationsByUserId(userId.value)
            Log.d("ScheduleNotificationsViewModel", notifications.toString())
            for (notification in notifications) {
                notificationHandler.scheduleNotification(notification.fecha.toJavaLocalDateTime(), notification.titulo, notification.mensaje)
            }
        }
        catch (e: Exception) {
            Log.e("ScheduleNotificationsViewModel", "Error scheduling notifications for user $userId", e)
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