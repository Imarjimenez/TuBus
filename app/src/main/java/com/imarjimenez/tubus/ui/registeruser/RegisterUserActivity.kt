package com.imarjimenez.tubus.ui.registeruser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.tubus.databinding.ActivityRegisterUserBinding
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity
import com.imarjimenez.tubus.ui.select_mode.SelectModeViewModel

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var registerUserBinding: ActivityRegisterUserBinding
    private lateinit var registerUserViewModel: RegisterUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    registerUserBinding = ActivityRegisterUserBinding.inflate(layoutInflater)
    setContentView(registerUserBinding.root)

    registerUserViewModel = ViewModelProvider(this).get(RegisterUserViewModel::class.java)

    registerUserViewModel.registerSuccess.observe(this){//va a la lista
        startActivity(Intent(this, MasterDetailActivity::class.java))
    }
    registerUserBinding.login2Button.setOnClickListener {
        val name = registerUserBinding.nameEditText.text.toString()
        val email = registerUserBinding.emailEditText.text.toString()
        val password = registerUserBinding.passwordEditText.text.toString()
        val passwordRepeat = registerUserBinding.repPasswordEditText.text.toString()
        val type = intent.getStringExtra("selectedType") ?: "user"

        registerUserViewModel.setEmail(email)
        registerUserViewModel.setPassword(password)
        registerUserViewModel.setPasswordRepeat(passwordRepeat)
        registerUserViewModel.setType(type)

        registerUserViewModel.validateFields(name,email, password,type)
    }

        registerUserViewModel.emailError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        registerUserViewModel.passwordError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        registerUserViewModel.passwordRepeatError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

