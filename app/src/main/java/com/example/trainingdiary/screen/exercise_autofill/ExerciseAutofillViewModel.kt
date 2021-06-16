package com.example.trainingdiary.screen.exercise_autofill

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.repository.ExerciseAutofillRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ExerciseAutofillViewModel(
    private val exerciseAutofillRepository: ExerciseAutofillRepository
) :
    CoroutineViewModel() {

    val autoCompleteExerciseLiveData =
        exerciseAutofillRepository.currentExerciseAutofillFlow.asLiveData()


    fun updateExerciseAutoFill(exerciseAutofill: ExerciseAutofill) {
        launch {
            exerciseAutofillRepository.updateExerciseAutofill(exerciseAutofill)
        }
    }

    fun deleteExerciseAutofill(exerciseAutofill: ExerciseAutofill) {
        launch {
            exerciseAutofillRepository.deleteExerciseAutofill(exerciseAutofill)
        }
    }
}