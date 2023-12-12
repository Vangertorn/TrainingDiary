package com.yankin.impl

import com.yankin.models.MuscleGroupDomain
import com.yankin.room.converters.toEntity
import com.yankin.room.converters.toModel
import com.yankin.room.dao.MuscleGroupDao
import com.yankin.storage.MuscleGroupStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MuscleGroupStorageImpl(
    private val db: MuscleGroupDao
) : MuscleGroupStorage {
    override fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroupDomain>?> {
        return db.getMuscleGroupsByFlagsFlow(flags).map {
            it?.map { it.toModel() }
        }
    }

    override fun getMuscleGroups(): List<MuscleGroupDomain>? {
        return db.getMuscleGroups()?.map { it.toModel() }
    }

    override fun deletedMuscleGroupFlags(muscleGroupDomainEntity: MuscleGroupDomain) {
        db.deletedMuscleGroupFlags(muscleGroupDomainEntity.toEntity())
    }

    override fun saveMuscleGroup(muscleGroupDomainEntity: MuscleGroupDomain) {
        db.saveMuscleGroup(muscleGroupDomainEntity.toEntity())
    }

    override fun saveMuscleGroups(muscleGroupDomainEntities: List<MuscleGroupDomain>) {
        db.saveMuscleGroups(muscleGroupDomainEntities.map { it.toEntity() })
    }

    override fun recoverDefaultValues(muscleGroupDomainEntities: List<MuscleGroupDomain>) {
        db.recoverDefaultValues(muscleGroupDomainEntities.map { it.toEntity() })
    }
}