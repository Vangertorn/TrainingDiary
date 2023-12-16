package com.yankin.muscle_groups.impl.data

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MuscleGroupRepositoryImpl @Inject constructor(
    private val muscleGroupLocalDataSource: MuscleGroupLocalDataSource,
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
        muscleGroupLocalDataSource.getMuscleGroupsByFlagsFlow(false)
            .map { it?: listOf() }.map { it.map {it.toDomain()

            } }

    override suspend fun getMuscleGroups(): List<MuscleGroupDomain> {
        return withContext(Dispatchers.IO) {
            return@withContext muscleGroupLocalDataSource.getMuscleGroups()?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun deleteMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(Dispatchers.IO) {
            muscleGroupLocalDataSource.deletedMuscleGroupFlags(muscleGroup.toEntity())
        }
    }

    override suspend fun saveMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(Dispatchers.IO) {
            muscleGroupLocalDataSource.saveMuscleGroup(muscleGroup.toEntity())
        }
    }

    override suspend fun saveDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(Dispatchers.IO) {
            muscleGroupLocalDataSource.saveMuscleGroups(defaultList.map { it.toEntity() })
        }
    }

    override suspend fun recoverDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(Dispatchers.IO) {
            muscleGroupLocalDataSource.recoverDefaultValues(defaultList.map { it.toEntity() })
        }
    }
}