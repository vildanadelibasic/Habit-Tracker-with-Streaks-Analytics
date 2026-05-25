package com.example.mobileprogrammingarchitecture.data.model.di

import com.example.mobileprogrammingarchitecture.data.repository.auth.AuthRepository
import com.example.mobileprogrammingarchitecture.data.repository.auth.impl.AuthRepositoryImpl
import com.example.mobileprogrammingarchitecture.data.repository.cloud.HabitCloudRepository
import com.example.mobileprogrammingarchitecture.data.repository.cloud.impl.HabitCloudRepositoryImpl
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
