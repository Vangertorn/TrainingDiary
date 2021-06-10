package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.MuscleGroupDao
import com.example.trainingdiary.models.MuscleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MuscleGroupRepository(private val muscleGroupDao: MuscleGroupDao) {

    suspend fun saveDefaultValues(muscleGroups: List<MuscleGroup>) {
        withContext(Dispatchers.IO) {
            muscleGroupDao.saveMuscleGroups(muscleGroups)
        }
    }

    val currentMuscleGroupFlow: Flow<List<MuscleGroup>> =
        muscleGroupDao.getMuscleGroupsFlow().map { it ?: listOf() }
}
