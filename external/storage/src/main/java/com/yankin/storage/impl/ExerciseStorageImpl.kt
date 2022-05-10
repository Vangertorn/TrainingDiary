package com.yankin.storage.impl

import com.yankin.models.ExerciseDomain
import com.yankin.models.info.ViewHolderTypesDomain
import com.yankin.storage.ExerciseStorage
import com.yankin.storage.room.converters.toEntity
import com.yankin.storage.room.converters.toModel
import com.yankin.storage.room.dao.ExerciseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseStorageImpl(
    private val db: ExerciseDao
) : ExerciseStorage {
    override fun getExerciseInfoFlow(idExercise: Long): Flow<ViewHolderTypesDomain.ExerciseInfoDomain?> {
        return db.getExerciseInfoFlow(idExercise).map { it?.toModel() }
    }

    override fun getExercisePositions(): MutableList<Int>? {
        return db.getExercisePositions()
    }

    override fun insertExercise(exerciseDomainEntity: ExerciseDomain) {
        db.insertExercise(exerciseDomainEntity.toEntity())
    }

    override fun deletedExercisesByFlags(flag: Boolean) {
        db.deletedExercisesByFlags(flag)
    }

    override fun deleteExercise(exerciseDomainEntity: ExerciseDomain) {
        db.deleteExercise(exerciseDomainEntity.toEntity())
    }

    override fun updateExercise(exerciseDomainEntity: ExerciseDomain) {
        db.updateExercise(exerciseDomainEntity.toEntity())
    }

    override fun deletedExerciseFlags(exerciseDomainEntity: ExerciseDomain) {
        db.deletedExerciseFlags(exerciseDomainEntity.toEntity())
    }

    override fun switchExercisePositions(
        exerciseDomain1Entity: ExerciseDomain,
        exerciseDomain2Entity: ExerciseDomain
    ) {
        db.switchExercisePositions(exerciseDomain1Entity.toEntity(), exerciseDomain2Entity.toEntity())
    }

    override fun deletedEmptyExercise(name: String) {
        db.deletedEmptyExercise(name)
    }

    override fun getExercisesInfoByBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<MutableList<ExerciseDomain>> {
        return db.getExercisesInfoByBySuperSetIdAndFlagsFlow(idSuperSet, flags)
            .map { it.map { it.toModel() }.toMutableList() }
    }

    override fun getExercisesInfoBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesDomain.ExerciseInfoDomain>> {
        return db.getExercisesInfoBySuperSetIdAndFlagsFlow(idSuperSet, flags)
            .map { it.map { it.toModel() } }
    }

    override fun getExerciseInfo(id: Long): ViewHolderTypesDomain.ExerciseInfoDomain {
        return db.getExerciseInfo(id).toModel()
    }

    override fun getListExerciseInfo(id: Long): List<ViewHolderTypesDomain.ExerciseInfoDomain> {
        return db.getListExerciseInfo(id).map { it.toModel() }
    }
}