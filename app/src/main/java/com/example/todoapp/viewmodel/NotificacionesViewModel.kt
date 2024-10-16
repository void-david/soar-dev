
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.data.Notificacion
import com.example.todoapp.data.NotificationDto
import com.example.todoapp.model.NotificationRepository
import com.example.todoapp.model.NotificationRepositoryImpl
import com.example.todoapp.viewmodel.NotificationViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overlay(navController: NavController){
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Agenda")
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* User icon action */ }) {
                Icon(Icons.Filled.Person, contentDescription = "User")
            }
        }
    )
}





@Composable
fun notificationViewModelMock() : NotificationViewModel {
    return NotificationViewModel(object : NotificationRepository {
        override suspend fun getNotifications(): List<NotificationDto> {
            return listOf(
                NotificationDto(1, LocalDateTime(1970, 1, 1, 0, 0), "Titulo 1", "Mensaje 1"),
                NotificationDto(2, LocalDateTime(2060, 1, 1, 0, 0), "Titulo 2", "Mensaje 2"),
                NotificationDto(3, LocalDateTime(2026, 1, 1, 0, 0), "Titulo 3", "Mensaje 3")
            )
        }

        override suspend fun getNotificationsByUserId(userId: Int): List<NotificationDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getNotification(id: Int): NotificationDto {
            return NotificationDto(1, LocalDateTime(1970, 1, 1, 0, 0), "Titulo 1", "Mensaje 1")
        }
    })
}


@Preview(showBackground = true)
@Composable
fun PreviewEjemplo() {
    NotificationListScreen(notificationViewModelMock())
}



@Composable
fun NotificationListScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val notificationList by notificationViewModel.notifications.collectAsState()
    LaunchedEffect(Unit) {
        notificationViewModel.getNotifications()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(notificationList.filter { notifications -> notifications.fecha < currentDate }) { notificacionItem ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                elevation = CardDefaults.elevatedCardElevation(5.dp)
            ) {

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(text = notificacionItem.titulo)
                    Text(text = notificacionItem.mensaje)
                    Text(text = notificacionItem.fecha.toString())

                }
            }
        }
    }
}
