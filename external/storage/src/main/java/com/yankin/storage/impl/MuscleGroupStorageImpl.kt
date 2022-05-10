package com.yankin.storage.impl

import com.yankin.models.MuscleGroupDomain
import com.yankin.storage.MuscleGroupStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.converters.toModel
import com.yankin.storage.room.dao.MuscleGroupDao
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