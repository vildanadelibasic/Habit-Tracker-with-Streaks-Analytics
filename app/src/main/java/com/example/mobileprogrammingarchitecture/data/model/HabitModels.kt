package com.example.mobileprogrammingarchitecture.data.model

enum class HabitDifficulty {
    Easy,
    Medium,
    Hard
}

data class HabitData(
    val id: Int,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val difficulty: HabitDifficulty = HabitDifficulty.Medium,
    val isDaily: Boolean = true
)

object HabitSampleDefaults {
    val initial: List<HabitData> = listOf(
        HabitData(1, "10k steps daily", "Walk or run", false, HabitDifficulty.Medium, true),
        HabitData(2, "Study", "University / books", false, HabitDifficulty.Hard, true),
        HabitData(3, "Coding", "Projects and practice", false, HabitDifficulty.Medium, true),
        HabitData(4, "Social time", "Coffee with friends", false, HabitDifficulty.Easy, false),
        HabitData(5, "Sleep", "8 hours", true, HabitDifficulty.Easy, true),
        HabitData(6, "2 L water daily", "Keep a bottle handy", false, HabitDifficulty.Easy, true),
        HabitData(7, "3 meals + snacks", "Regular meals", false, HabitDifficulty.Medium, true)
    )
}
