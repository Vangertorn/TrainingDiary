package com.yankin.exercise.impl.data

import com.yankin.room.dao.ExerciseDao
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

internal class ExerciseLocalDataSource @Inject constructor(
    private val db: ExerciseDao
) {
    fun getExerciseInfoFlow(exerciseId: Long): Flow<ExerciseEntity?> {
        return db.getExerciseFlow(exerciseId)
    }

    fun getExercisePositions(): MutableList<Int>? {
        return db.getExercisePositions()
    }

    fun insertExercise(exercise: ExerciseEntity) {
        db.insertExercise(exercise)
    }

    fun deletedExercisesByFlag(flag: Boolean) {
        db.deletedExercisesByFlag(flag)
    }

    fun deleteExerciseById(exerciseId: Long) {
        db.deleteExerciseById(exerciseId)
    }

    fun updateExerciseDeleteFlagById(exerciseId: Long, deleteFlag: Boolean) {
        db.deleteExerciseById(exerciseId)
    }

    fun updateExercise(exercise: ExerciseEntity) {
        db.updateExercise(exercise)
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
}