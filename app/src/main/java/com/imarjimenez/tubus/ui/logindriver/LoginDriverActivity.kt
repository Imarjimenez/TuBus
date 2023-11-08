package com.imarjimenez.tubus.ui.logindriver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import com.imarjimenez.tubus.databinding.ActivityLoginDriverBinding
import com.imarjimenez.tubus.ui.logindriver.LoginDriverViewModel
import com.imarjimenez.tubus.ui.masterdetail.MasterDetailActivity

class LoginDriverActivity : AppCompatActivity() {
    private lateinit var loginDriverBinding: ActivityLoginDriverBinding
    private lateinit var loginDriverviewModel: LoginDriverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginDriverBinding = ActivityLoginDriverBinding.inflate(layoutInflater)
        setContentView(loginDriverBinding.root)

        loginDriverviewModel = ViewModelProvider(this).get(LoginDriverViewModel::class.java)

        loginDriverviewModel.loginSuccess.observe(this){//va a la lista
            startActivity(Intent(this, MasterDetailActivity::class.java))
            finish()
        }
        loginDriverBinding.login2Button.setOnClickListener {
            val email = loginDriverBinding.emailDriverEditText.text.toString()
            val password = loginDriverBinding.passwordDriverEditText.text.toString()
            val type: String = intent.getStringExtra("type") ?: ""


            loginDriverviewModel.setEmail(email)
            loginDriverviewModel.setPassword(password)


            loginDriverviewModel.validateFields(email, password,type)

        }

        loginDriverviewModel.errorMsg.observe(this, {error ->
            error?.let { showToast(it) }

        })
        loginDriverviewModel.emailError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })

        loginDriverviewModel.passwordError.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}