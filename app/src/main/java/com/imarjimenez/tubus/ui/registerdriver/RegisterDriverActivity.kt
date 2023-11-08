package com.imarjimenez.tubus.ui.registerdriver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.tubus.databinding.ActivityRegisterDriverBinding
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity

class RegisterDriverActivity : AppCompatActivity() {
    private lateinit var registerDriverBinding: ActivityRegisterDriverBinding
    private lateinit var registerDriverViewModel: RegisterDriverViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerDriverBinding = ActivityRegisterDriverBinding.inflate(layoutInflater)
        setContentView(registerDriverBinding.root)

        registerDriverViewModel = ViewModelProvider(this).get(RegisterDriverViewModel::class.java)

        registerDriverViewModel.registerSuccess.observe(this){//va a la lista
            startActivity(Intent(this, MasterDetailActivity::class.java))
        }
        registerDriverBinding.login2Button.setOnClickListener {
            val name = registerDriverBinding.nameDriverEditText.text.toString()
            val email = registerDriverBinding.emailDriverEditText.text.toString()
            val password = registerDriverBinding.passwordDriverEditText.text.toString()
            val type = intent.getStringExtra("selectedType") ?: "driver"
            val passwordRepeat = registerDriverBinding.repPasswordDriverEditText.text.toString()

            registerDriverViewModel.setEmail(email)
            registerDriverViewModel.setPassword(password)
            registerDriverViewModel.setPasswordRepeat(passwordRepeat)

            registerDriverViewModel.validateFields(name,email, password, type)
        }

        registerDriverViewModel.emailError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        registerDriverViewModel.passwordError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        registerDriverViewModel.passwordRepeatError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}