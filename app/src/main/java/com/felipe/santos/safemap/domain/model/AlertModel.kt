package com.felipe.santos.safemap.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class AlertModel(
    val id: String = "",
    val type: AlertType = AlertType.VERBAL,
    val description: String = "",
    val location: GeoPoint = GeoPoint(0.0, 0.0),
    val timestamp: Timestamp = Timestamp.now(),
    val createdBy: String = "",
    val confirmations: Int = 0
) {
    val latitude: Double
        get() = location.latitude

    val longitude: Double
        get() = location.longitude
}