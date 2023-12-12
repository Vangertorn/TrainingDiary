package com.yankin.trainingdiary.repository

import android.content.Context
import com.yankin.storage.MuscleGroupStorage
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.models.MuscleGroup
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MuscleGroupRepository(
    private val muscleGroupStorage: MuscleGroupStorage,
    context: Context
) {

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
        muscleGroupStorage.getMuscleGroupsByFlagsFlow(false)
            .map { it?.map { it.toModel() } ?: listOf() }

    suspend fun getMuscleGroups(): List<MuscleGroup> {
        return withContext(Dispatchers.IO) {
            return@withContext muscleGroupStorage.getMuscleGroups()?.map { it.toModel() }
                ?: emptyList()
        }
    }

    suspend fun deleteMuscleGroup(muscleGroup: MuscleGroup) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.deletedMuscleGroupFlags(muscleGroup.toDomain())
        }
    }

    suspend fun saveMuscleGroup(muscleGroup: MuscleGroup) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.saveMuscleGroup(muscleGroup.toDomain())
        }
    }

    suspend fun saveDefaultValues() {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.saveMuscleGroups(list.map { it.toDomain() })
        }
    }

    suspend fun recoverDefaultValues() {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.recoverDefaultValues(list.map {
                it.toDomain()
            })
        }
    }
}
