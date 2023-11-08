package com.imarjimenez.tubus.ui.logindriver
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imarjimenez.tubus.data.ResourceRemote
import com.imarjimenez.tubus.data.DriverRepository
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginDriverViewModel : ViewModel() {
    private val driverRepository = DriverRepository()
    private var db = Firebase.firestore

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
        if (_credentialsAreValid.value == true){
            if(type=="driver") {
                viewModelScope.launch {
                    val result = driverRepository.loginDriver(email, password)
                    result.let { resourceRemote ->
                        when (resourceRemote) {

                            is ResourceRemote.Success -> {
                                _errorMsg.postValue("Ingresaste correctamente")
                                _loginSuccess.postValue(true)
                                val userType = getUserTypeFromDatabase(result.data)
                                if (userType == "user") {
                                    _errorMsg.postValue("Ingresaste como conductor")
                                    _loginSuccess.postValue(true)
                                }  else {
                                    _errorMsg.postValue("No tienes acceso como conductor")
                                }
                            }

                            is ResourceRemote.Error -> {
                                var msg = result.message
                                when (msg) {
                                    "An internal error has ocurred. [ INVALID_LOGIN_CREDENTIALS ]" -> msg =
                                        "Correo electronico o contraseña invalido"

                                    "A network error (such as timeout, interrupted connection or unreachable host) has ocurred." -> msg =
                                        "Revise su conexión a internet"
                                }

                                _errorMsg.postValue(msg)
                            }

                            else -> {
                                //dont use
                            }

                        }

                    }
                }
            }
            else{
                _errorMsg.postValue("No tienes acceso como conductor, recuerda registrarte en modo conductor")
            }

        }
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


}