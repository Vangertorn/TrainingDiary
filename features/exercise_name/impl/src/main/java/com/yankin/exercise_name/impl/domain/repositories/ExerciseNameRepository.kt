package com.yankin.exercise_name.impl.domain.repositories

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import kotlinx.coroutines.flow.Flow

interface ExerciseNameRepository {

    val currentExerciseNameAsStringStream: Flow<List<String>>

    val currentExerciseNameStream: Flow<List<ExerciseNameDomain>>

    suspend fun saveExerciseName(exerciseName: ExerciseNameDomain)

    suspend fun deleteExerciseName(exerciseName: ExerciseNameDomain)

    suspend fun updateExerciseName(exerciseName: ExerciseNameDomain)
}