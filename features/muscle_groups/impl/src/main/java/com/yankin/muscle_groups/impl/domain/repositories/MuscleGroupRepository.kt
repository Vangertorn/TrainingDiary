package com.yankin.muscle_groups.impl.domain.repositories

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import kotlinx.coroutines.flow.Flow

interface MuscleGroupRepository {

    val currentMuscleGroupFlow: Flow<List<MuscleGroupDomain>>

    suspend fun getMuscleGroups(): List<MuscleGroupDomain>

    suspend fun deleteMuscleGroup(muscleGroupId: Long)

    suspend fun saveMuscleGroup(muscleGroup: MuscleGroupDomain)

    suspend fun saveDefaultValues(defaultList: List<MuscleGroupDomain>)

    suspend fun recoverDefaultValues()
}