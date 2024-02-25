package com.yankin.exercise.impl.data

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.room.dao.ExerciseDao
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

internal class ExerciseLocalDataSource @Inject constructor(
    private val db: ExerciseDao
) {

    fun getExercisePositions(): MutableList<Int>? {
        return db.getExercisePositions()
    }

    fun insertExercise(exercise: ExerciseEntity) {
        db.insertExercise(exercise)
    }

    fun deletedExercisesByFlag(flag: Boolean) {
        db.deletedExercisesByFlag(flag)
    }

    fun updateExerciseDeleteFlagById(exerciseId: Long, deleteFlag: Boolean) {
        db.updateExerciseDeleteFlagById(exerciseId = exerciseId, deleteFlag = deleteFlag)
    }

    fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?) {
        db.updateExercise(
            exerciseId = exerciseId, exerciseName = exerciseName, exerciseComment = exerciseComment
        )
    }

    fun switchExercisePositions(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    ) {
        db.switchExercisePositions(
            firstExerciseId = firstExerciseId,
            firstExercisePosition = firstExercisePosition,
            secondExerciseId = secondExerciseId,
            secondExercisePosition = secondExercisePosition,
        )
    }

    fun deletedEmptyExercise() {
        db.deletedEmptyExercise()
    }

    fun getExerciseListByTrainingIdStream(
        trainingId: Long,
    ): Flow<List<ExerciseEntity>> {
        return db.getExercisesByTrainingIdFlow(trainingId = trainingId, flags = false).filterNotNull()
    }

    fun getExerciseListBySuperSetId(
        superSetId: Long,
    ): List<ExerciseEntity> {
        return db.getExercisesInfoBySuperSetId(superSetId = superSetId, flags = false)
    }

    fun getExerciseListBySuperSetIdStream(
        superSetId: Long,
    ): Flow<List<ExerciseEntity>> {
        return db.getExercisesInfoBySuperSetIdFlow(superSetId = superSetId, flags = false)
    }

    fun getExerciseByIdStream(exerciseId: Long): Flow<ExerciseEntity>{
        return db.getExerciseFromIdStream(exerciseId)
    }
}