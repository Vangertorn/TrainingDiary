package com.example.trainingdiary.repository

import android.content.Context
import com.example.trainingdiary.R
import com.example.trainingdiary.dao.database.MuscleGroupDao
import com.example.trainingdiary.models.MuscleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MuscleGroupRepository(private val muscleGroupDao: MuscleGroupDao, context: Context) {

    private val list = listOf(
        MuscleGroup(nameMuscleGroup = context.getString(R.string.legs), factorySettings = true),
        MuscleGroup(
            nameMuscleGroup = context.getString(R.string.all_muscle_groups),
            factorySettings = true
        ),
        MuscleGroup(nameMuscleGroup = context.getString(R.string.breast), factorySettings = true),
        MuscleGroup(nameMuscleGroup = context.getString(R.string.biceps), factorySettings = true),
        MuscleGroup(
            nameMuscleGroup = context.getString(R.string.shoulders),
            factorySettings = true
        ),
        MuscleGroup(nameMuscleGroup = context.getString(R.string.back), factorySettings = true),
        MuscleGroup(nameMuscleGroup = context.getString(R.string.triceps), factorySettings = true)
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
