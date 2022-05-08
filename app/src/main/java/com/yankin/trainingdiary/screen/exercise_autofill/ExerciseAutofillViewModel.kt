package com.yankin.trainingdiary.screen.exercise_autofill

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.models.ExerciseAutofill
import com.yankin.trainingdiary.repository.ExerciseAutofillRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseAutofillViewModel @Inject constructor(
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