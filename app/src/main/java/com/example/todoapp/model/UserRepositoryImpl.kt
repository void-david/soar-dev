package com.example.todoapp.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.data.CitasDtoUpload
import com.example.todoapp.data.ClienteDto
import com.example.todoapp.data.ClienteDtoUpload
import com.example.todoapp.data.EmpleadoDto
import com.example.todoapp.data.EmpleadoDtoUpload
import com.example.todoapp.data.UsuarioDto
import com.example.todoapp.data.UsuarioDtoUpload
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.admin.AdminUserUpdateBuilder
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.toJsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val postgrest: Postgrest,
    private val auth: Auth,
) : UserRepository {

    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    override val sessionState: StateFlow<SessionStatus> get() = _sessionState

    private val _errorMessage = MutableStateFlow<String>("")
    override val errorMessage: StateFlow<String> get() = _errorMessage

    private val _username = MutableStateFlow<String>("")
    override val username: StateFlow<String> get() = _username

    private val _role = MutableStateFlow<String>("")
    override val role: StateFlow<String> get() = _role

    private val _userId = MutableStateFlow<Int>(0)
    override val userId: StateFlow<Int> get() = _userId

    init {
        CoroutineScope(Dispatchers.IO).launch {
            // Listener para cambios de sesión
            supabaseClient.auth.sessionStatus.collect { sessionStatus ->
                Log.d("UserRepository", "Session status changed: $sessionStatus")
                _sessionState.value = sessionStatus
            }
        }
    }

    override suspend fun checkRole(){
        withContext(Dispatchers.Main) {
            auth.currentUserOrNull()?.email?.let { _username.value = it }
        }
        Log.d("UserRepository", "Username: ${username.value}")
        if (username.value != "") {
            checkUserId(username.value)
            delay(500)
            Log.d("UserRepository", "User ID: ${userId.value}")
            _role.value = checkIfUserIdInTable(userId.value) ?: ""
            Log.d("UserRepository", "Role: ${role.value}")
        }
    }

    override suspend fun updatePassword(username: String, password: String) {
        withContext(Dispatchers.Main){
            checkUserId(username)
        }
        try {
            postgrest.from("Usuario")
                .update({ set("contrasena", password) }){
                    filter {
                        eq("usuario_id", userId.value)
                    }
                }
            withContext(Dispatchers.Main){
                auth.admin.updateUserById(auth.currentUserOrNull()?.id ?: ""){
                    this.password = password
                }
            }
        } catch (e: Exception){
            e.localizedMessage?.let { Log.e("UserRepositoryImpl", it) }
        }
    }

    override suspend fun getEmpleado(): List<EmpleadoDto> {
        return try {
                Log.d("UserRepository", "Fetching Empleado...")

                val result = postgrest.from("Empleado")
                    .select()
                    .decodeList<EmpleadoDto>()
                Log.d("UserRepository", "Fetched Empleado: $result")
                result

        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching Empleado: ${e.localizedMessage}", e)
            emptyList()
        }
    }

    override suspend fun getUsuarioById(userId: Int): UsuarioDto? {
        var resultUsuario: UsuarioDto? = null
        try{
            resultUsuario = postgrest.from("Usuario")
                .select {
                    filter {
                        eq("usuario_id", userId)
                    }
                }.decodeSingleOrNull<UsuarioDto>()
            Log.d("UserRepositoryImplGetUsuarioById", "Fetched Usuario: $resultUsuario")
        } catch (e: Exception) {
            Log.e("UserRepositoryImplGetUsuarioById", "Error fetching Usuario: ${e.localizedMessage}", e)
        }
        return resultUsuario
    }

    val isLoading = mutableStateOf(false)

    override suspend fun signIn(userEmail: String, userPassword: String): Boolean {
        isLoading.value = true
        _errorMessage.value = ""
        return try {
            auth.signInWith(Email) {
                this.email = userEmail
                this.password = userPassword
            }
            if (sessionState.value is SessionStatus.Authenticated) {
                Log.d("UserRepositoryImpl", "Sign-in successful for: $userEmail")
                return true
            } else {
                Log.e("UserRepositoryImpl", "Sign-in failed, session not authenticated")
                Log.d("UserRepositoryImpl", auth.sessionStatus.toString())
                Log.d("UserRepositoryImpl", "Session status: ${sessionState.value}")
                return false
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message ?: "Unknown error"
            Log.d("UserRepositoryImpl", _errorMessage.value)
            false
        } finally {
            isLoading.value = false
        }
    }

    override suspend fun signUp(
        cliente: ClienteDtoUpload,
        usuario: UsuarioDtoUpload,
        userEmail: String,
        userPassword: String
    ): Boolean {
        Log.d("UserRepositoryImpl", "Attempting sign-up with email: $userEmail")
        return try {
            auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
            try {
                withContext(Dispatchers.Main) {
                    val usuarioDto = UsuarioDtoUpload(
                        username = usuario.username,
                        password = usuario.password,
                        phone = usuario.phone,
                        name = usuario.name,
                        lastName1 = usuario.lastName1,
                        lastName2 = usuario.lastName2,
                        role = usuario.role
                    )

                    postgrest.from("Usuario").insert(usuario)
                    Log.d("UserRepositoryImpl", "Inserted Usuario: $usuarioDto")
                }
                delay(500)
                withContext(Dispatchers.Main) {
                    checkUserId(usuario.username)
                }
                delay(500)
                val clienteDto = ClienteDtoUpload(
                    usuarioId = userId.value,
                    ciudad = cliente.ciudad,
                    sector = cliente.sector,
                    calle = cliente.calle,
                    numero = cliente.numero
                )
                postgrest.from("Cliente").insert(clienteDto)
                Log.d("UserRepositoryImpl", "Inserted Cliente: $clienteDto")
                true
            } catch (e: Exception) {
                Log.e("UserRepositoryImpl", "Inserting Cliente failed: ${e.message}")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Sign-up failed: ${e.message}")
            false
        }
    }

    override suspend fun empleadoSignUp(
        empleado: EmpleadoDtoUpload,
        usuario: UsuarioDtoUpload,
        userEmail: String,
        userPassword: String
    ): Boolean {
        Log.d("UserRepositoryImpl", "Attempting sign-up with email: $userEmail")
        return try {
            auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
            try {
                withContext(Dispatchers.Main) {
                    val usuarioDto = UsuarioDtoUpload(
                        username = usuario.username,
                        password = usuario.password,
                        phone = usuario.phone,
                        name = usuario.name,
                        lastName1 = usuario.lastName1,
                        lastName2 = usuario.lastName2,
                        role = usuario.role
                    )

                    postgrest.from("Usuario").insert(usuario)
                    Log.d("UserRepositoryImpl", "Inserted Usuario: $usuarioDto")
                }
                delay(500)
                withContext(Dispatchers.Main) {
                    checkUserId(usuario.username)
                }
                delay(500)
                val empleadoDto = EmpleadoDtoUpload(
                    matricula = empleado.matricula,
                    admin = empleado.admin,
                    usuarioId = userId.value,
                    jefeId = empleado.jefeId
                )
                postgrest.from("Empleado").insert(empleadoDto)
                Log.d("UserRepositoryImpl", "Inserted Empleado: $empleadoDto")
                true
            } catch (e: Exception) {
                Log.e("UserRepositoryImpl", "Inserting Empleado failed: ${e.message}")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Sign-up failed: ${e.message}")
            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun checkIfUserIdInTable(userId: Int): String? {
        var isInCliente = false
        var isInEmpleado = false
        var isInUsuario = false

        var resultUsuario: UsuarioDto? = null
        Log.d("UserRepositoryImpl", "userId: $userId")

        try {
            val resultEmpleado = postgrest.from("Empleado")
                .select {
                    filter {
                        eq("usuario_id", userId)
                    }
                }.decodeSingleOrNull<EmpleadoDto>()
            Log.d("UserRepositoryImpl", "Fetched Empleado: $resultEmpleado")

            if (resultEmpleado != null) {
                isInEmpleado = true
            }
            val resultCliente = postgrest.from("Cliente")
                .select {
                    filter {
                        eq("usuario_id", userId)
                    }
                }.decodeSingleOrNull<ClienteDto>()

            if (resultCliente != null) {
                isInCliente = true
            }


        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Error fetching User: ${e.localizedMessage}", e)
        }

        Log.d("UserRepositoryImpl", "isInEmpleado: $isInEmpleado, isInCliente: $isInCliente")

        return when {
            isInEmpleado && isInCliente -> null
            isInEmpleado -> "Empleado"
            isInCliente -> "Cliente"
            else -> null
        }
    }

    @OptIn(SupabaseInternal::class)
    override suspend fun checkUserId(username: String) {
        try {
            val result = postgrest.from("Usuario")
                .select {
                    filter {
                        eq("username", username)
                    }
                }.decodeSingleOrNull<UsuarioDto>()
            Log.d("UserRepositoryImpl", "Fetched User: ${result?.usuarioId}")
            _userId.value = result?.usuarioId ?: 0
                }
        catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Error fetching User: ${e.localizedMessage}", e)
        }
    }

    override suspend fun updateUsuario(usuario: UsuarioDto) {
        Log.d("UpdateUserRepoImpl", "Updating User with id: ${usuario.usuarioId}")
        try{
            withContext(Dispatchers.IO){
                postgrest.from("Usuario")
                    .update({
                        set("nombre", usuario.name)
                        set("apellido1", usuario.lastName1)
                        set("apellido2", usuario.lastName2)
                        set("username", usuario.username)
                        set("telefono", usuario.phone)
                    }){
                        filter {
                            eq("usuario_id", usuario.usuarioId)
                        }
                    }
            }
        } catch(e: Exception){
            Log.e("UpdateUserRepoImpl", "Error updating User: ${e.localizedMessage}", e)
        }
    }

    override suspend fun checkAdmin(userId: Int): Boolean {
        val result = postgrest.from("Empleado")
            .select{
                filter {
                    eq("usuario_id", userId)
                }
            }.decodeSingleOrNull<EmpleadoDto>()
        Log.d("UserRepositoryImpl", "Fetched Empleado: ${result?.admin}")
        return result?.admin ?: false
    }

}
