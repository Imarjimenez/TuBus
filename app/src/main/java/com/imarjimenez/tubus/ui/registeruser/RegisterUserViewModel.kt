package com.imarjimenez.tubus.ui.registeruser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imarjimenez.tubus.data.ResourceRemote
import com.imarjimenez.tubus.data.UserRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import com.imarjimenez.tubus.ui.model.User

class RegisterUserViewModel :  ViewModel() {

    private val userRepository = UserRepository()

    private val _navigateToActivity = MutableLiveData<Class<*>>()
    val navigateToActivity: LiveData<Class<*>>
        get() = _navigateToActivity

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> =_errorMsg

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _type = MutableLiveData<String>()
    val type: LiveData<String>
        get() = _type

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _passwordRepeat = MutableLiveData<String>()
    val passwordRepeat: LiveData<String>
        get() = _passwordRepeat

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?>
        get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?>
        get() = _passwordError

    private val _passwordRepeatError = MutableLiveData<String?>()
    val passwordRepeatError: LiveData<String?>
        get() = _passwordRepeatError

    private val _credentialsAreValid = MutableLiveData<Boolean>()
    val credentialsAreValid: LiveData<Boolean>
        get() = _credentialsAreValid

    private val _registerSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    fun setEmail(email: String) {
        _email.value = email
    }
    fun setType(type: String) {
        _type.value = type
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setPasswordRepeat(passwordRepeat: String) {
        _passwordRepeat.value = passwordRepeat
    }

    fun validateFields(name: String,email: String, password: String, type: String) {
        _emailError.value = if (isEmailValid()) null else "Correo electrónico inválido"
        _passwordError.value = if (isPasswordValid()) null else "Contraseña inválida"
        _passwordRepeatError.value = if (arePasswordsMatching()) null else "Las contraseñas no coinciden"
        _credentialsAreValid.value = areCredentialsValid()
        if (_credentialsAreValid.value == true){
            viewModelScope.launch {
                val result = userRepository.registerUser(email, password)
                result.let {resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success -> {
                            val uid = result.data
                            uid?.let {Log.d("uid User", it)}
                            val user = User(uid,email,name,type)
                            createUser(user)

                        }
                        is ResourceRemote.Error ->{
                            var msg = result.message
                            when(msg){
                                "The email address is already in use by another account." -> msg = "Ya existe una cuenta con ese correo electrónico"
                                "A network error (such as timeout, interrupted connection or unreachable host) has ocurred." -> msg ="Revise su conexión a internet"
                                "The email address is already in use by another account." -> "Ese correo ya existe"
                            }

                            _errorMsg.postValue(msg)
                        }

                        else ->{
                            //dont use
                        }

                    }

                }
            }
        }
    }

    private fun createUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.createUser(user)
            result.let { resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success ->{
                        _registerSuccess.postValue(true)
                        _errorMsg.postValue("Usuario creado exitosamente")
                    }
                    is ResourceRemote.Error ->{
                        var msg = result.message
                        _errorMsg.postValue(msg)
                    }
                    else ->{
                        //don't use
                    }

                }

            }
        }
    }



    private fun areCredentialsValid(): Boolean {
        return isEmailValid() && isPasswordValid() && arePasswordsMatching()
    }
    private fun isEmailValid(): Boolean {
        val email = _email.value ?: return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(): Boolean {
        val password = _password.value ?: return false
        return password.length >= 6
    }

    private fun arePasswordsMatching(): Boolean {
        val password = _password.value
        val passwordRepeat = _passwordRepeat.value
        return password == passwordRepeat
    }

}