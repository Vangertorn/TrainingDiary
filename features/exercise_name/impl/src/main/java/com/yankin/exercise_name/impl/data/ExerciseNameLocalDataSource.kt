package com.yankin.exercise_name.impl.data

import com.yankin.room.dao.ExerciseNameDao
import com.yankin.room.entity.ExerciseNameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ExerciseNameLocalDataSource@Inject constructor(
    private val db: ExerciseNameDao
) {
     fun getExerciseNameAsStringStream(): Flow<List<String>?> {
        return db.getExerciseAutofillStringFlow()
    }

     fun getExerciseNameStream(): Flow<List<ExerciseNameEntity>?> {
        return db.getExerciseAutofillFlow()
    }

     fun insertExerciseName(exerciseNameDomainEntity: ExerciseNameEntity) {
         db.insertExerciseAutofill(exerciseNameDomainEntity)
    }

     fun deleteExerciseName(exerciseNameDomainEntity: ExerciseNameEntity) {
         db.deleteExerciseAutofill(exerciseNameDomainEntity)
    }

     fun updateExerciseName(exerciseNameDomainEntity: ExerciseNameEntity) {
         db.updateExerciseAutofill(exerciseNameDomainEntity)
    }
}