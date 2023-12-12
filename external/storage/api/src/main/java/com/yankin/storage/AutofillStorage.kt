package com.yankin.storage

import com.yankin.models.ExerciseAutofillDomain
import kotlinx.coroutines.flow.Flow

interface AutofillStorage {

    fun getExerciseAutofillStringFlow(): Flow<List<String>?>

    fun getExerciseAutofillFlow(): Flow<List<ExerciseAutofillDomain>?>

    fun insertExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain)

    fun deleteExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain)

    fun updateExerciseAutofill(exerciseAutofillDomainEntity: ExerciseAutofillDomain)
}