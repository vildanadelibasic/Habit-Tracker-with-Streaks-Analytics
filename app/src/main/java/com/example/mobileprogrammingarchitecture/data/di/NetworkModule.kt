package com.example.mobileprogrammingarchitecture.data.di

import com.example.mobileprogrammingarchitecture.BuildConfig
import com.example.mobileprogrammingarchitecture.data.datasource.network.service.HabitApiService
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRemoteRepository
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideHabitApiService(retrofit: Retrofit): HabitApiService =
        retrofit.create(HabitApiService::class.java)

    @Provides
    @Singleton
    fun provideHabitRemoteRepository(apiService: HabitApiService): HabitRemoteRepository =
        HabitRemoteRepositoryImpl(apiService)
}
