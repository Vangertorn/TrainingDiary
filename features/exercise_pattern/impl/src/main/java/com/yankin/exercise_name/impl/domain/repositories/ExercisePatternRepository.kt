package com.yankin.exercise_name.impl.domain.repositories

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import kotlinx.coroutines.flow.Flow

interface ExercisePatternRepository {

    val currentExercisePatternAsStringStream: Flow<List<String>>

    val currentExercisePatternStream: Flow<List<ExercisePatternDomain>>

    suspend fun saveExercisePattern(exercisePattern: ExercisePatternDomain)

    suspend fun deleteExercisePattern(exercisePattern: ExercisePatternDomain)

    suspend fun updateExercisePattern(exercisePattern: ExercisePatternDomain)
}