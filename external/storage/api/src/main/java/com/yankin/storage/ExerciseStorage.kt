package com.yankin.storage

import com.yankin.models.ExerciseDomain
import com.yankin.models.info.ViewHolderTypesDomain
import kotlinx.coroutines.flow.Flow

interface ExerciseStorage {

    fun getExerciseInfoFlow(idExercise: Long): Flow<ViewHolderTypesDomain.ExerciseInfoDomain?>

    fun getExercisePositions(): MutableList<Int>?

    fun insertExercise(exerciseDomainEntity: ExerciseDomain)

    fun deletedExercisesByFlags(flag: Boolean)

    fun deleteExercise(exerciseDomainEntity: ExerciseDomain)

    fun updateExercise(exerciseDomainEntity: ExerciseDomain)

    fun deletedExerciseFlags(exerciseDomainEntity: ExerciseDomain)

    fun switchExercisePositions(exerciseDomain1Entity: ExerciseDomain, exerciseDomain2Entity: ExerciseDomain)

    fun deletedEmptyExercise(name: String)

    fun getExercisesInfoByBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<MutableList<ExerciseDomain>>

    fun getExercisesInfoBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesDomain.ExerciseInfoDomain>>

    fun getExerciseInfo(id: Long): ViewHolderTypesDomain.ExerciseInfoDomain

    fun getListExerciseInfo(id: Long): List<ViewHolderTypesDomain.ExerciseInfoDomain>

    fun getExercisesInfoByTrainingIdAndFlagsFlow(
        idTraining: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesDomain.ExerciseInfoDomain>>
}