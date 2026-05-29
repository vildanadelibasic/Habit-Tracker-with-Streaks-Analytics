package com.example.mobileprogrammingarchitecture.data.di

import com.example.mobileprogrammingarchitecture.domain.repository.AuthRepository
import com.example.mobileprogrammingarchitecture.domain.repository.AuthRepositoryImpl
import com.example.mobileprogrammingarchitecture.domain.repository.HabitCloudRepository
import com.example.mobileprogrammingarchitecture.domain.repository.HabitCloudRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideHabitCloudRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): HabitCloudRepository = HabitCloudRepositoryImpl(firestore, firebaseAuth)
}
