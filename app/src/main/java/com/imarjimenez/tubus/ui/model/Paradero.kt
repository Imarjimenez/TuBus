package com.imarjimenez.tubus.ui.model

import com.google.firebase.firestore.GeoPoint

data class Paradero(
    var uid: String? = null,
    var name: String?= null,
    var location: GeoPoint?= null,
    var image: Int
)