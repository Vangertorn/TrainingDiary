package com.yankin.exercise.api.repositories

import com.yankin.exercise.api.models.ExerciseDomain
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercisesByTrainingBlockIdStream(trainingBlockId: Long): Flow<List<ExerciseDomain>>

    suspend fun saveExercise(exercise: ExerciseDomain)

    suspend fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?)

    fun getExerciseListBySuperSetIdStream(superSetId: Long): Flow<List<ExerciseDomain>>

    fun getExerciseByIdStream(exerciseId: Long): Flow<ExerciseDomain>
}