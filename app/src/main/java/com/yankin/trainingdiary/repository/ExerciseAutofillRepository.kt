package com.yankin.trainingdiary.repository

import com.yankin.storage.AutofillStorage
import com.yankin.trainingdiary.models.ExerciseAutofill
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ExerciseAutofillRepository(
    private val autofillStorage: AutofillStorage
) {

    val currentExerciseAutofillStringFlow: Flow<List<String>> =
        autofillStorage.getExerciseAutofillStringFlow().map {
            it ?: emptyList()
        }
    val currentExerciseAutofillFlow: Flow<List<ExerciseAutofill>> =
        autofillStorage.getExerciseAutofillFlow().map {
            it?.map { entity ->
                entity.toModel()
            } ?: emptyList()
        }

    suspend fun saveExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            autofillStorage.insertExerciseAutofill(exerciseAutofill.toDomain())
        }
    }

    suspend fun deleteExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            autofillStorage.deleteExerciseAutofill(exerciseAutofill.toDomain())
        }
    }

    suspend fun updateExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        withContext(Dispatchers.IO) {
            autofillStorage.updateExerciseAutofill(exerciseAutofill.toDomain())
        }
    }
}
