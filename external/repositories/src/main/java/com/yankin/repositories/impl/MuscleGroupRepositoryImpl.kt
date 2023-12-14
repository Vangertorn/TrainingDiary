package com.yankin.repositories.impl

import com.yankin.domain.api.model.MuscleGroupDomain
import com.yankin.domain.api.repositories.MuscleGroupRepository
import com.yankin.storage.MuscleGroupStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class MuscleGroupRepositoryImpl(
    private val muscleGroupStorage: MuscleGroupStorage,
    //    context: Context
) : MuscleGroupRepository {

//    private val list = listOf(
//        MuscleGroup(nameMuscleGroup = context.getString(R.string.legs), factorySettings = true),
//        MuscleGroup(
//            nameMuscleGroup = context.getString(R.string.all_muscle_groups),
//            factorySettings = true
//        ),
//        MuscleGroup(nameMuscleGroup = context.getString(R.string.breast), factorySettings = true),
//        MuscleGroup(nameMuscleGroup = context.getString(R.string.biceps), factorySettings = true),
//        MuscleGroup(
//            nameMuscleGroup = context.getString(R.string.shoulders),
//            factorySettings = true
//        ),
//        MuscleGroup(nameMuscleGroup = context.getString(R.string.back), factorySettings = true),
//        MuscleGroup(nameMuscleGroup = context.getString(R.string.triceps), factorySettings = true)
//    )

    override val currentMuscleGroupFlow: Flow<List<MuscleGroupDomain>> =
        muscleGroupStorage.getMuscleGroupsByFlagsFlow(false)
            .map { it?: listOf() }

    override suspend fun getMuscleGroups(): List<MuscleGroupDomain> {
        return withContext(Dispatchers.IO) {
            return@withContext muscleGroupStorage.getMuscleGroups() ?: emptyList()
        }
    }

    override suspend fun deleteMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.deletedMuscleGroupFlags(muscleGroup)
        }
    }

    override suspend fun saveMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.saveMuscleGroup(muscleGroup)
        }
    }

    override suspend fun saveDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.saveMuscleGroups(defaultList)
        }
    }

    override suspend fun recoverDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(Dispatchers.IO) {
            muscleGroupStorage.recoverDefaultValues(defaultList)
        }
    }
}