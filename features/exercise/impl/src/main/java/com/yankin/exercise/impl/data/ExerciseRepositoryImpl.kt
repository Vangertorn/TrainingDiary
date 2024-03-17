package com.yankin.exercise.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.repositories.ExerciseRepository
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseLocalDataSource: ExerciseLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ExerciseRepository {

    override fun getExercisesByTrainingBlockIdStream(trainingBlockId: Long): Flow<List<ExerciseDomain>> {
        return exerciseLocalDataSource.getExerciseListByTrainingBlockIdStream(trainingBlockId).map { exerciseEntityList ->
            exerciseEntityList.map { exerciseEntity ->
                exerciseEntity.toDomain()
            }
        }
    }

    override suspend fun saveExercise(exercise: ExerciseDomain) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.insertExercise(
                ExerciseEntity(
                    name = exercise.name,
                    trainingBlockId = exercise.trainingBlockId,
                    position = exercise.position,
                    comment = exercise.comment,
                )
            )
        }
    }

    override suspend fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?) {
        withContext(coroutineDispatchers.io) {
            exerciseLocalDataSource.updateExercise(
                exerciseId = exerciseId, exerciseName = exerciseName, exerciseComment = exerciseComment
            )
        }
    }

    override fun getExerciseListBySuperSetIdStream(superSetId: Long): Flow<List<ExerciseDomain>> {
        return exerciseLocalDataSource.getExerciseListBySuperSetIdStream(superSetId).map { exerciseEntityList ->
            exerciseEntityList.map { exerciseEntity ->
                exerciseEntity.toDomain()
            }
        }
    }

    override fun getExerciseByIdStream(exerciseId: Long): Flow<ExerciseDomain> {
        return exerciseLocalDataSource.getExerciseByIdStream(exerciseId).map { exerciseEntity ->
            exerciseEntity.toDomain()
        }
    }
}
