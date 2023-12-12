package com.yankin.storage

import com.yankin.models.MuscleGroupDomain
import kotlinx.coroutines.flow.Flow

interface MuscleGroupStorage {

    fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroupDomain>?>

    fun getMuscleGroups(): List<MuscleGroupDomain>?

    fun deletedMuscleGroupFlags(muscleGroupDomainEntity: MuscleGroupDomain)

    fun saveMuscleGroup(muscleGroupDomainEntity: MuscleGroupDomain)

    fun saveMuscleGroups(muscleGroupDomainEntities: List<MuscleGroupDomain>)

    fun recoverDefaultValues(muscleGroupDomainEntities: List<MuscleGroupDomain>)
}