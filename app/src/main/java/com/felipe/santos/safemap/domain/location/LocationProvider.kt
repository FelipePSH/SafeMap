package com.felipe.santos.safemap.domain.location

import android.location.Location

interface LocationProvider {
    suspend fun getLastKnownLocation(): Location?
}