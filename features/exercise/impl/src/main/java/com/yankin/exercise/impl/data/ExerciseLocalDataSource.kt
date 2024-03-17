package com.yankin.exercise.impl.data

import com.yankin.room.dao.ExerciseDao
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExerciseLocalDataSource @Inject constructor(
    private val db: ExerciseDao
) {

    fun insertExercise(exercise: ExerciseEntity) {
        db.insertExercise(exercise)
    }

    fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?) {
        db.updateExercise(
            exerciseId = exerciseId, exerciseName = exerciseName, exerciseComment = exerciseComment
        )
    }

    fun getExerciseListByTrainingBlockIdStream(
        trainingBlockId: Long,
    ): Flow<List<ExerciseEntity>> {
        return db.getExercisesByTrainingBlockIdStream(trainingBlockId = trainingBlockId).map { exerciseEntityList ->
            exerciseEntityList ?: emptyList()
        }
    }

    fun getExerciseListBySuperSetIdStream(
        superSetId: Long,
    ): Flow<List<ExerciseEntity>> {
        return db.getExercisesInfoBySuperSetIdFlow(superSetId = superSetId)
    }

    fun getExerciseByIdStream(exerciseId: Long): Flow<ExerciseEntity>{
        return db.getExerciseFromIdStream(exerciseId)
    }
}