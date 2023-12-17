package com.yankin.exercise_name.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import com.yankin.room.entity.ExerciseNameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ExerciseNameRepositoryImpl @Inject constructor(
    private val exerciseNameLocalDataSource: ExerciseNameLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ExerciseNameRepository {
    override val currentExerciseNameAsStringStream: Flow<List<String>> =
        exerciseNameLocalDataSource.getExerciseNameAsStringStream().filterNotNull()
    override val currentExerciseNameStream: Flow<List<ExerciseNameDomain>> =
        exerciseNameLocalDataSource.getExerciseNameStream()
            .filterNotNull()
            .map { exerciseNameEntityList ->
                exerciseNameEntityList.map(ExerciseNameEntity::toModel)
            }

    override suspend fun saveExerciseName(exerciseName: ExerciseNameDomain) {
        withContext(coroutineDispatchers.io) {
            exerciseNameLocalDataSource.insertExerciseName(exerciseName.toEntity())
        }
    }

    override suspend fun deleteExerciseName(exerciseName: ExerciseNameDomain) {
        withContext(coroutineDispatchers.io) {
            exerciseNameLocalDataSource.deleteExerciseName(exerciseName.toEntity())
        }
    }

    override suspend fun updateExerciseName(exerciseName: ExerciseNameDomain) {
        withContext(coroutineDispatchers.io) {
            exerciseNameLocalDataSource.updateExerciseName(exerciseName.toEntity())
        }
    }
}