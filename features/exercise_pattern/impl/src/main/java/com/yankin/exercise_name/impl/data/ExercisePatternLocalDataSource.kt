package com.yankin.exercise_name.impl.data

import com.yankin.room.dao.ExercisePatternDao
import com.yankin.room.entity.ExercisePatternEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ExercisePatternLocalDataSource @Inject constructor(
    private val db: ExercisePatternDao
) {
    fun getExercisePatternAsStringStream(): Flow<List<String>?> {
        return db.getExercisePatternStringFlow()
    }

    fun getExercisePatternStream(): Flow<List<ExercisePatternEntity>?> {
        return db.getExercisePatternFlow()
    }

    fun insertExercisePattern(exercisePatternDomainEntity: ExercisePatternEntity) {
        db.insert(exercisePatternDomainEntity)
    }

    fun deleteExercisePattern(exercisePatternId: Long) {
        db.delete(exercisePatternId)
    }

    fun updateExercisePattern(exercisePatternDomainEntity: ExercisePatternEntity) {
        db.update(exercisePatternDomainEntity)
    }

    fun getExercisePatternById(exercisePatternId: Long): ExercisePatternEntity {
        return db.getExercisePatternById(exercisePatternId)
    }
}