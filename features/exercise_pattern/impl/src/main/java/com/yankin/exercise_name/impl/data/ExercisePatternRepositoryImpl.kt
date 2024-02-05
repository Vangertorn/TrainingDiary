package com.yankin.exercise_name.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import com.yankin.room.entity.ExercisePatternEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ExercisePatternRepositoryImpl @Inject constructor(
    private val exercisePatternLocalDataSource: ExercisePatternLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ExercisePatternRepository {
    override val currentExercisePatternAsStringStream: Flow<List<String>> =
        exercisePatternLocalDataSource.getExercisePatternAsStringStream().filterNotNull()
    override val currentExercisePatternStream: Flow<List<ExercisePatternDomain>> =
        exercisePatternLocalDataSource.getExercisePatternStream()
            .filterNotNull()
            .map { exercisePatternEntityList ->
                exercisePatternEntityList.map(ExercisePatternEntity::toModel)
            }

    override suspend fun saveExercisePattern(exercisePattern: ExercisePatternDomain) {
        withContext(coroutineDispatchers.io) {
            exercisePatternLocalDataSource.insertExercisePattern(exercisePattern.toEntity())
        }
    }

    override suspend fun deleteExercisePattern(exercisePattern: ExercisePatternDomain) {
        withContext(coroutineDispatchers.io) {
            exercisePatternLocalDataSource.deleteExercisePattern(exercisePattern.toEntity())
        }
    }

    override suspend fun updateExercisePattern(exercisePattern: ExercisePatternDomain) {
        withContext(coroutineDispatchers.io) {
            exercisePatternLocalDataSource.updateExercisePattern(exercisePattern.toEntity())
        }
    }
}