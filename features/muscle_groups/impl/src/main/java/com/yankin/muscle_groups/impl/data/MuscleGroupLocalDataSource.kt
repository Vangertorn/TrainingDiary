package com.yankin.muscle_groups.impl.data

import com.yankin.room.dao.MuscleGroupDao
import com.yankin.room.entity.MuscleGroupEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MuscleGroupLocalDataSource @Inject constructor(
    private val db: MuscleGroupDao
)  {
     fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroupEntity>?> {
        return db.getMuscleGroupsByFlagsFlow(flags)
    }

     fun getMuscleGroups(): List<MuscleGroupEntity>? {
        return db.getMuscleGroups()
    }

     fun deletedMuscleGroupFlags(muscleGroupDomainEntity:MuscleGroupEntity) {
        db.deletedMuscleGroupFlags(muscleGroupDomainEntity)
    }

     fun saveMuscleGroup(muscleGroupDomainEntity: MuscleGroupEntity) {
        db.saveMuscleGroup(muscleGroupDomainEntity)
    }

     fun saveMuscleGroups(muscleGroupDomainEntities: List<MuscleGroupEntity>) {
        db.saveMuscleGroups(muscleGroupDomainEntities)
    }

     fun recoverDefaultValues(muscleGroupDomainEntities: List<MuscleGroupEntity>) {
        db.recoverDefaultValues(muscleGroupDomainEntities)
    }
}