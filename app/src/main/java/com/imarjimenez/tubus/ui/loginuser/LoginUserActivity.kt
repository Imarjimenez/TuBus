package com.imarjimenez.tubus.ui.loginuser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.tubus.databinding.ActivityLoginUserBinding
import android.widget.Toast
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity

class LoginUserActivity : AppCompatActivity() {

    private lateinit var loginUserBinding: ActivityLoginUserBinding
    private lateinit var loginUserviewModel: LoginUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginUserBinding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(loginUserBinding.root)

        loginUserviewModel = ViewModelProvider(this).get(LoginUserViewModel::class.java)

        loginUserviewModel.loginSuccess.observe(this){//va a la lista
            startActivity(Intent(this, MasterDetailActivity::class.java))
            finish()
        }
        loginUserBinding.login2Button.setOnClickListener {
            val email = loginUserBinding.emailUserEditText.text.toString()
            val password = loginUserBinding.passwordUserEditText.text.toString()
            val type: String = intent.getStringExtra("type") ?: ""

            loginUserviewModel.setEmail(email)
            loginUserviewModel.setPassword(password)


            loginUserviewModel.validateFields(email, password,type)

        }

        loginUserviewModel.emailError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        loginUserviewModel.passwordError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })
        loginUserviewModel.errorMsg.observe(this, {error ->
            error?.let { showToast(it) }

        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }




}