package com.imarjimenez.tubus.ui.loginuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imarjimenez.tubus.data.ResourceRemote
import com.imarjimenez.tubus.data.UserRepository
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginUserViewModel : ViewModel() {

    private var db = Firebase.firestore

    private val userRepository = UserRepository()

    private val _navigateToActivity = MutableLiveData<Class<*>>()
    val navigateToActivity: LiveData<Class<*>>
        get() = _navigateToActivity

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> =_errorMsg

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?>
        get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?>
        get() = _passwordError

    private val _credentialsAreValid = MutableLiveData<Boolean>()
    val credentialsAreValid: LiveData<Boolean>
        get() = _credentialsAreValid

    private val _loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }


    fun validateFields(email: String, password: String, type: String) {
        _emailError.value = if (isEmailValid()) null else "Correo electrónico inválido"
        _passwordError.value = if (isPasswordValid()) null else "Contraseña inválida"
        _credentialsAreValid.value = areCredentialsValid()
        if (_credentialsAreValid.value == true) {
            viewModelScope.launch {
                val result = userRepository.loginUser(email, password)
                result.let { resourceRemote ->
                    when (resourceRemote) {
                        is ResourceRemote.Success -> {
                            // Ahora, vamos a verificar el tipo de usuario desde la base de datos
                            val userType = getUserTypeFromDatabase(result.data)
                            if (userType == "user") {
                                _errorMsg.postValue("Ingresaste como usuario")
                                _loginSuccess.postValue(true)
                            }  else {
                                _errorMsg.postValue("No tienes acceso como usuario")
                            }
                        }
                        is ResourceRemote.Error -> {
                            var msg = result.message
                            when (msg) {
                                "An internal error has ocurred. [ INVALID_LOGIN_CREDENTIALS ]" -> msg =
                                    "Correo electrónico o contraseña inválidos"
                                "A network error (such as timeout, interrupted connection, or unreachable host) has occurred." -> msg =
                                    "Revise su conexión a internet"
                            }
                            _errorMsg.postValue(msg)
                        }
                        else -> {
                            // No se usa
                        }
                    }
                }
            }
        }
    }

    private suspend fun getUserTypeFromDatabase(uid: String?): String {
        if (uid != null) {
            val userDocument = db.collection("Users").document(uid)
            val userSnapshot = userDocument.get().await()
            if (userSnapshot.exists()) {
                return userSnapshot.getString("type") ?: ""
            }
        }
        return ""
    }

    private fun isEmailValid(): Boolean {
        val email = _email.value ?: return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(): Boolean {
        val password = _password.value ?: return false
        return password.length >= 6
    }


    private fun areCredentialsValid(): Boolean {
        return isEmailValid() && isPasswordValid()
    }
    fun onMasterDetailButtonClick() {
        _navigateToActivity.value = MasterDetailActivity::class.java
    }
}