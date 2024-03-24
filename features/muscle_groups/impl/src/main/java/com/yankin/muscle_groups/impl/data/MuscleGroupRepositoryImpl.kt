package com.yankin.muscle_groups.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.repositories.MuscleGroupRepository
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

    override suspend fun getMuscleGroupsByIds(muscleGroupsIds: List<Long>): List<MuscleGroupDomain> {
        return withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.getMuscleGroupsByIds(muscleGroupsIds).map(MuscleGroupEntity::toDomain)
        }
    }

    override suspend fun deleteMuscleGroup(muscleGroupId: Long) {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.deletedMuscleGroupById(muscleGroupId)
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

    override suspend fun recoverDefaultValues() {
        withContext(coroutineDispatchers.io) {
            muscleGroupLocalDataSource.recoverDefaultValues()
        }
    }
}