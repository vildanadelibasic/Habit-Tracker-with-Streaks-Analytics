package com.example.mobileprogrammingarchitecture.model.repository.habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.model.datasource.network.service.HabitApiService
import com.example.mobileprogrammingarchitecture.domain.repository.HabitRemoteRepository
import com.example.mobileprogrammingarchitecture.model.mapper.HabitMapper
import javax.inject.Inject

class HabitRemoteRepositoryImpl @Inject constructor(
    private val api: HabitApiService
) : HabitRemoteRepository {

    override suspend fun getHabits(): List<HabitData> =
        api.getHabits().map(HabitMapper::toDomain)

    override suspend fun getHabitById(id: Int): HabitData =
        HabitMapper.toDomain(api.getHabitById(id))

    override suspend fun createHabit(habit: HabitData): HabitData =
        HabitMapper.toDomain(api.createHabit(HabitMapper.toCreateDto(habit)))

    override suspend fun updateHabit(id: Int, habit: HabitData): HabitData =
        HabitMapper.toDomain(api.updateHabit(id, HabitMapper.toUpdateDto(habit)))

    override suspend fun deleteHabit(id: Int) {
        api.deleteHabit(id)
    }
}
