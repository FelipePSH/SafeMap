package com.felipe.santos.safemap.data.di

import com.felipe.santos.safemap.data.location.LocationProviderImpl
import com.felipe.santos.safemap.domain.location.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationProvider(
        locationProviderImpl: LocationProviderImpl
    ): LocationProvider
}