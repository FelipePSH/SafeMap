package com.felipe.santos.safemap.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.felipe.santos.safemap.domain.location.LocationProvider
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(
   @ApplicationContext private val context: Context
) : LocationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Location? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return try {
            fusedLocationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }
}