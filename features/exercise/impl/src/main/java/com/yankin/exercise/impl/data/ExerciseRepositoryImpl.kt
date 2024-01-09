package com.yankin.exercise.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import com.yankin.preferences.AppSettings
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseLocalDataSource: ExerciseLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
    appSettings: AppSettings,
) : ExerciseRepository {
    override val currentExerciseListStream: Flow<List<ExerciseDomain>> = appSettings.idTrainingFlow()
        .flatMapLatest { idTraining ->
            exerciseLocalDataSource.getExerciseListByTrainingIdStream(idTraining).map { exerciseEntityList ->
                exerciseEntityList.map { exerciseEntity ->
                    exerciseEntity.toDomain()
                }
            }
        }

    override suspend fun saveExercise(exercise: ExerciseDomain) {
        withContext(coroutineDispatchers.io) {
            if (exercise.id == 0L) {
                val listPositions: MutableList<Int> =
                    exerciseLocalDataSource.getExercisePositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                exerciseLocalDataSource.insertExercise(
                    ExerciseEntity(
                        name = exercise.name,
                        idTraining = exercise.idTraining,
                        position = listPositions[0] - 1,
                        comment = exercise.comment,
                        idSet = exercise.idSet
                    )
                )
            }
        }
    }

    override suspend fun updateExercise(exercise: ExerciseDomain) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.updateExercise(exercise.toEntity())
        }
    }

    override suspend fun deletedExerciseTrue(exerciseId: Long) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.updateExerciseDeleteFlagById(
                exerciseId = exerciseId, deleteFlag = true
            )
        }
    }

    override suspend fun deletedExerciseFalse(exerciseId: Long) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.updateExerciseDeleteFlagById(
                exerciseId = exerciseId, deleteFlag = false
            )
        }
    }

    override suspend fun getExerciseListBySuperSetId(superSetId: Long): List<ExerciseDomain> {
        return withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.getExerciseListBySuperSetId(superSetId).map { exerciseEntity ->
                exerciseEntity.toDomain()
            }
        }
    }

    override suspend fun getExerciseListBySuperSetIdStream(superSetId: Long): Flow<List<ExerciseDomain>> {
        return exerciseLocalDataSource.getExerciseListBySuperSetIdStream(superSetId).map { exerciseEntityList ->
            exerciseEntityList.map { exerciseEntity ->
                exerciseEntity.toDomain()
            }
        }
    }

    override suspend fun deleteExercises() {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.deletedExercisesByFlag(true)
        }
    }

    override suspend fun deleteEmptyExercise() {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.deletedEmptyExercise()
        }
    }

    override suspend fun switchExercisePosition(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    ) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.switchExercisePositions(
                firstExerciseId = firstExerciseId,
                firstExercisePosition = firstExercisePosition,
                secondExerciseId = secondExerciseId,
                secondExercisePosition = secondExercisePosition,
            )
        }
    }
}
