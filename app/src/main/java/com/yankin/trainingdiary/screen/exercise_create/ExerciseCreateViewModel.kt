package com.yankin.trainingdiary.screen.exercise_create

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseAutofill
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.repository.ExerciseAutofillRepository
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ExerciseCreateViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseAutofillRepository: ExerciseAutofillRepository,
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings
) : CoroutineViewModel() {

    val autoCompleteExerciseStringLiveData =
        exerciseAutofillRepository.currentExerciseAutofillStringFlow.asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            exerciseRepository.saveExercise(exercise)
        }
    }

    fun addNewExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        launch {
            exerciseAutofillRepository.saveExerciseAutofill(exerciseAutofill)
        }
    }

    fun saveSuperSet(superSet: SuperSet, exercise: Exercise, emptyExercise: Exercise): Long {
        return runBlocking {
            superSetRepository.saveSuperSet(superSet, exercise, emptyExercise)
        }
    }

    fun saveSuperSetId(id: Long) {
        launch {
            appSettings.setSuperSetId(id)
        }
    }
}