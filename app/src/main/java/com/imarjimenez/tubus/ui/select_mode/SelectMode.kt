package com.imarjimenez.tubus.ui.select_mode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.imarjimenez.tubus.R
import com.imarjimenez.tubus.ui.loginuser.LoginUserActivity
import com.imarjimenez.tubus.ui.registeruser.RegisterUserActivity
import com.imarjimenez.tubus.databinding.ActivitySelectModeBinding
import com.imarjimenez.tubus.ui.logindriver.LoginDriverActivity
import com.imarjimenez.tubus.ui.registerdriver.RegisterDriverActivity
class SelectMode : AppCompatActivity() {
    private lateinit var selectModeBinding: ActivitySelectModeBinding
    private lateinit var selectModeViewModel: SelectModeViewModel
    private var selectedType: String = "" // Variable para almacenar el tipo seleccionado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectModeBinding = ActivitySelectModeBinding.inflate(layoutInflater)
        setContentView(selectModeBinding.root)

        selectModeViewModel = ViewModelProvider(this).get(SelectModeViewModel::class.java)

        selectModeBinding.loginButton.setOnClickListener {
            // Establecer el tipo seleccionado
            selectedType = when {
                selectModeBinding.userRadioButton.isChecked -> "user"
                selectModeBinding.driverRadioButton.isChecked -> "driver"
                else -> ""
            }

            // Iniciar la actividad correspondiente
            if (selectedType.isNotEmpty()) {
                val intent = when (selectedType) {
                    "user" -> Intent(this, LoginUserActivity::class.java)
                    "driver" -> Intent(this, LoginDriverActivity::class.java)
                    else -> null
                }

                intent?.let {
                    startActivity(it)
                }
            }
        }

        selectModeBinding.registerButton.setOnClickListener {
            // Establecer el tipo seleccionado
            selectedType = when {
                selectModeBinding.userRadioButton.isChecked -> "user"
                selectModeBinding.driverRadioButton.isChecked -> "driver"
                else -> ""
            }

            // Iniciar la actividad correspondiente
            if (selectedType.isNotEmpty()) {
                val intent = when (selectedType) {
                    "user" -> Intent(this, RegisterUserActivity::class.java)
                    "driver" -> Intent(this, RegisterDriverActivity::class.java)
                    else -> null
                }

                intent?.let {
                    startActivity(it)
                }
            }
        }
    }
}