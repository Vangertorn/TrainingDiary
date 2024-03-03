package com.yankin.exercise.impl.domain.repositories

import com.yankin.exercise.api.models.ExerciseDomain
import kotlinx.coroutines.flow.Flow

internal interface ExerciseRepository {

    fun getExercisesByTrainingIdStream(trainingId: Long): Flow<List<ExerciseDomain>>

    suspend fun saveExercise(exercise: ExerciseDomain)

    suspend fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?)

    suspend fun deletedExerciseTrue(exerciseId: Long)

    suspend fun deletedExerciseFalse(exerciseId: Long)

    suspend fun getExerciseListBySuperSetId(superSetId: Long): List<ExerciseDomain>

    fun getExerciseListBySuperSetIdStream(superSetId: Long): Flow<List<ExerciseDomain>>

    suspend fun deleteExercises()

    suspend fun deleteEmptyExercise()

    suspend fun switchExercisePosition(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    )

    fun getExerciseByIdStream(exerciseId: Long): Flow<ExerciseDomain>
}