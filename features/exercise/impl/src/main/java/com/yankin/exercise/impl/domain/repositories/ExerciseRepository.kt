package com.yankin.exercise.impl.domain.repositories

import com.yankin.exercise.api.models.ExerciseDomain
import kotlinx.coroutines.flow.Flow

internal interface ExerciseRepository {

    val currentExerciseListStream: Flow<List<ExerciseDomain>>

    suspend fun saveExercise(exercise: ExerciseDomain)

    suspend fun updateExercise(exercise: ExerciseDomain)

    suspend fun deletedExerciseTrue(exerciseId: Long)

    suspend fun deletedExerciseFalse(exerciseId: Long)

    suspend fun getExerciseListBySuperSetId(superSetId: Long): List<ExerciseDomain>

    suspend fun getExerciseListBySuperSetIdStream(superSetId: Long): Flow<List<ExerciseDomain>>

    suspend fun deleteExercises()

    suspend fun deleteEmptyExercise()

    suspend fun switchExercisePosition(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    )
}