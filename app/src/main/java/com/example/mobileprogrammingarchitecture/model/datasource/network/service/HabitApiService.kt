package com.example.mobileprogrammingarchitecture.model.datasource.network.service

import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.CreateHabitDto
import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.HabitDto
import com.example.mobileprogrammingarchitecture.model.datasource.network.dto.UpdateHabitDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HabitApiService {

    @GET("habits/")
    suspend fun getHabits(
        @Header("X-Authentication") authHeader: String = "yes"
    ): List<HabitDto>

    @GET("habits/{id}")
    suspend fun getHabitById(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    ): HabitDto

    @POST("habits/")
    suspend fun createHabit(
        @Body habit: CreateHabitDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): HabitDto

    @PUT("habits/{id}")
    suspend fun updateHabit(
        @Path("id") id: Int,
        @Body habit: UpdateHabitDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): HabitDto

    @DELETE("habits/{id}")
    suspend fun deleteHabit(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    )
}
