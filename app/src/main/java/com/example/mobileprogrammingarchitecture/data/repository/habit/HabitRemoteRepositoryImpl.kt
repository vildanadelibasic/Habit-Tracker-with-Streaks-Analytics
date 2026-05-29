package com.example.mobileprogrammingarchitecture.data.repository.habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.data.datasource.network.service.HabitApiService
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRemoteRepository
import com.example.mobileprogrammingarchitecture.data.mapper.HabitNetworkMapper
import javax.inject.Inject

class HabitRemoteRepositoryImpl @Inject constructor(
    private val api: HabitApiService
) : HabitRemoteRepository {

    override suspend fun getHabits(): List<HabitData> =
        api.getHabits().map(HabitNetworkMapper::toDomain)

    override suspend fun getHabitById(id: Int): HabitData =
        HabitNetworkMapper.toDomain(api.getHabitById(id))

    override suspend fun createHabit(habit: HabitData): HabitData =
        HabitNetworkMapper.toDomain(api.createHabit(HabitNetworkMapper.toCreateDto(habit)))

    override suspend fun updateHabit(id: Int, habit: HabitData): HabitData =
        HabitNetworkMapper.toDomain(api.updateHabit(id, HabitNetworkMapper.toUpdateDto(habit)))

    override suspend fun deleteHabit(id: Int) {
        api.deleteHabit(id)
    }
}
