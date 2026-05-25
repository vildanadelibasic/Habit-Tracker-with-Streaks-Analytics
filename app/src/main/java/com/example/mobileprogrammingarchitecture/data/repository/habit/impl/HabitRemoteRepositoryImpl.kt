package com.example.mobileprogrammingarchitecture.data.repository.habit.impl

import com.example.mobileprogrammingarchitecture.data.model.HabitData
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.CreateHabitDto
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.dto.UpdateHabitDto
import com.example.mobileprogrammingarchitecture.data.model.datasource.network.service.HabitApiService
import com.example.mobileprogrammingarchitecture.data.repository.habit.HabitRemoteRepository
import com.example.mobileprogrammingarchitecture.data.repository.mappers.HabitNetworkMapper
import javax.inject.Inject

class HabitRemoteRepositoryImpl @Inject constructor(
    private val api: HabitApiService
) : HabitRemoteRepository {

    override suspend fun getHabits(): List<HabitData> =
        api.getHabits().map(HabitNetworkMapper::toDomain)

    override suspend fun getHabitById(id: Int): HabitData =
        HabitNetworkMapper.toDomain(api.getHabitById(id))

    override suspend fun createHabit(habit: CreateHabitDto): HabitData =
        HabitNetworkMapper.toDomain(api.createHabit(habit))

    override suspend fun updateHabit(id: Int, habit: UpdateHabitDto): HabitData =
        HabitNetworkMapper.toDomain(api.updateHabit(id, habit))

    override suspend fun deleteHabit(id: Int) {
        api.deleteHabit(id)
    }
}
