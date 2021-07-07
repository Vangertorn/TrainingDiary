package com.example.trainingdiary.screen.exercise_create


import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.models.SuperSet
import com.example.trainingdiary.repository.ExerciseAutofillRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.SuperSetRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ExerciseCreateViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseAutofillRepository: ExerciseAutofillRepository,
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings
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
    fun saveSuperSet(superSet: SuperSet, exercise: Exercise, emptyExercise: Exercise): Long{
        return runBlocking {
            superSetRepository.saveSuperSet(superSet, exercise, emptyExercise)
        }
    }

    fun saveSuperSetId(id: Long){
        launch {
            appSettings.setSuperSetId(id)
        }
    }
}