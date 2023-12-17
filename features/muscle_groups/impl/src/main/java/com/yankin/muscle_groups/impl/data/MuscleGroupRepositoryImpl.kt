package com.yankin.muscle_groups.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import com.yankin.room.entity.MuscleGroupEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MuscleGroupRepositoryImpl @Inject constructor(
    private val muscleGroupLocalDataSource: MuscleGroupLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MuscleGroupRepository {

    override val currentMuscleGroupFlow: Flow<List<MuscleGroupDomain>> =
        muscleGroupLocalDataSource.getMuscleGroupsByFlagsFlow(false)
            .map { muscleGroupEntityList ->
                muscleGroupEntityList?.map(MuscleGroupEntity::toDomain) ?: emptyList()
            }

    override suspend fun getMuscleGroups(): List<MuscleGroupDomain> = withContext(coroutineDispatchers.io) {
        muscleGroupLocalDataSource.getMuscleGroups()?.map(MuscleGroupEntity::toDomain) ?: emptyList()
    }

    override suspend fun deleteMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.deletedMuscleGroupFlags(muscleGroup.toEntity())
        }
    }

    override suspend fun saveMuscleGroup(muscleGroup: MuscleGroupDomain) {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.saveMuscleGroup(muscleGroup.toEntity())
        }
    }

    override suspend fun saveDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.saveMuscleGroups(defaultList.map(MuscleGroupDomain::toEntity))
        }
    }

    override suspend fun recoverDefaultValues(defaultList: List<MuscleGroupDomain>) {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.recoverDefaultValues(defaultList.map(MuscleGroupDomain::toEntity))
        }
    }
}