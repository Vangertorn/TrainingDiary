package com.example.trainingdiary.screen.exercise_create


import androidx.lifecycle.asLiveData
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.repository.ExerciseAutofillRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ExerciseCreateViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseAutofillRepository: ExerciseAutofillRepository
) : CoroutineViewModel() {

    val autoCompleteExerciseStringLiveData = exerciseAutofillRepository.currentExerciseAutofillStringFlow.asLiveData()


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
}