package com.yankin.trainingdiary.repository

import com.yankin.trainingdiary.dao.database.ExerciseAutofillDao
import com.yankin.trainingdiary.models.ExerciseAutofill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ExerciseAutofillRepository(private val exerciseAutofillDao: ExerciseAutofillDao) {

    val currentExerciseAutofillStringFlow: Flow<List<String>> =
        exerciseAutofillDao.getExerciseAutofillStringFlow().map {
            it ?: emptyList()
        }
    val currentExerciseAutofillFlow: Flow<List<ExerciseAutofill>> =
        exerciseAutofillDao.getExerciseAutofillFlow().map {
            it ?: emptyList()
        }

    suspend fun saveExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            exerciseAutofillDao.insertExerciseAutofill(exerciseAutofill)
        }
    }

    suspend fun deleteExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            exerciseAutofillDao.deleteExerciseAutofill(exerciseAutofill)
        }
    }

    suspend fun updateExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            exerciseAutofillDao.updateExerciseAutofill(exerciseAutofill)
        }
    }
}
