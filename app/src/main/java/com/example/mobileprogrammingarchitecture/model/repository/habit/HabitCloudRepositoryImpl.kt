package com.example.mobileprogrammingarchitecture.model.repository.habit

import com.example.mobileprogrammingarchitecture.domain.data.HabitData
import com.example.mobileprogrammingarchitecture.domain.data.HabitDifficulty
import com.example.mobileprogrammingarchitecture.model.util.HabitFirestoreEntity
import com.example.mobileprogrammingarchitecture.domain.repository.HabitCloudRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HabitCloudRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : HabitCloudRepository {

    private val collection = firestore.collection(COLLECTION_HABITS)

    override fun observeCloudHabits(): Flow<List<HabitData>> = callbackFlow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        var registration: ListenerRegistration? = null
        registration = collection
            .whereEqualTo(FIELD_USER_ID, userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val habits = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(HabitFirestoreEntity::class.java)?.let { entity ->
                        toDomain(entity.copy(id = doc.id))
                    }
                }.orEmpty()
                trySend(habits)
            }

        awaitClose { registration?.remove() }
    }

    override suspend fun upsertHabit(habit: HabitData) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        val payload = HabitFirestoreEntity(
            userId = userId,
            title = habit.title,
            description = habit.description,
            frequency = if (habit.isDaily) "daily" else "weekly",
            completed = habit.isCompleted
        )
        val docId = if (habit.id > 0) "habit_${habit.id}" else collection.document().id
        collection.document(docId).set(payload).await()
    }

    override suspend fun deleteHabit(documentId: String) {
        collection.document(documentId).delete().await()
    }

    private fun toDomain(entity: HabitFirestoreEntity): HabitData {
        val numericId = entity.id.removePrefix("habit_").toIntOrNull() ?: entity.id.hashCode()
        return HabitData(
            id = numericId,
            title = entity.title,
            description = entity.description,
            isCompleted = entity.completed,
            difficulty = when (entity.frequency.lowercase()) {
                "daily" -> HabitDifficulty.Easy
                "hard" -> HabitDifficulty.Hard
                else -> HabitDifficulty.Medium
            },
            isDaily = entity.frequency.equals("daily", ignoreCase = true)
        )
    }

    companion object {
        private const val COLLECTION_HABITS = "habits"
        private const val FIELD_USER_ID = "userId"
    }
}
