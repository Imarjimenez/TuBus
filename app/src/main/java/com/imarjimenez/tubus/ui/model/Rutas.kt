package com.imarjimenez.tubus.ui.model

import java.io.Serializable

data class Rutas(
    var uid: String? = null,
    var name: String?= null,
    var image: Int
) : Serializable