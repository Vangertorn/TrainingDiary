package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.MuscleGroupDao
import com.example.trainingdiary.models.MuscleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MuscleGroupRepository(private val muscleGroupDao: MuscleGroupDao) {

    private val list = listOf(
        MuscleGroup(nameMuscleGroup = "Legs", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "All muscle groups", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "Breast", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "Biceps", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "Shoulders", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "Back", factorySettings = true),
        MuscleGroup(nameMuscleGroup = "Triceps", factorySettings = true)
    )


    val currentMuscleGroupFlow: Flow<List<MuscleGroup>> =
        muscleGroupDao.getMuscleGroupsByFlagsFlow(false).map { it ?: listOf() }

    suspend fun getMuscleGroups(): List<MuscleGroup> {
        return withContext(Dispatchers.IO) {
            return@withContext muscleGroupDao.getMuscleGroups() ?: emptyList()
        }
    }

    suspend fun deleteMuscleGroup(muscleGroup: MuscleGroup) {
        withContext(Dispatchers.IO) {
            muscleGroupDao.deletedMuscleGroupFlags(muscleGroup)
        }
    }

    suspend fun saveMuscleGroup(muscleGroup: MuscleGroup) {
        withContext(Dispatchers.IO) {
            muscleGroupDao.saveMuscleGroup(muscleGroup)
        }
    }

    suspend fun saveDefaultValues() {
        withContext(Dispatchers.IO) {
            muscleGroupDao.saveMuscleGroups(list)
        }
    }

    suspend fun recoverDefaultValues() {
        withContext(Dispatchers.IO) {
            muscleGroupDao.recoverDefaultValues(list)
        }
    }


}
