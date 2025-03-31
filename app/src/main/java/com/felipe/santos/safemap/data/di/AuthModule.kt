package com.felipe.santos.safemap.data.di

import com.felipe.santos.safemap.data.repository.InviteRepositoryImpl
import com.felipe.santos.safemap.domain.repository.InviteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideInviteRepository(
        firestore: FirebaseFirestore
    ): InviteRepository = InviteRepositoryImpl(firestore)
}