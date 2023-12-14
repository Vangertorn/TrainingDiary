package com.yankin.domain.api.repositories

import com.yankin.domain.api.model.MuscleGroupDomain
import kotlinx.coroutines.flow.Flow

interface MuscleGroupRepository {

    val currentMuscleGroupFlow: Flow<List<MuscleGroupDomain>>

    suspend fun getMuscleGroups(): List<MuscleGroupDomain>

    suspend fun deleteMuscleGroup(muscleGroup: MuscleGroupDomain)

    suspend fun saveMuscleGroup(muscleGroup: MuscleGroupDomain)

    suspend fun saveDefaultValues(defaultList: List<MuscleGroupDomain>)

    suspend fun recoverDefaultValues(defaultList: List<MuscleGroupDomain>)
}