package com.example.trainingdiary.screen.super_set_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.ExerciseAutofill
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.ExerciseAutofillRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.SuperSetRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SuperSetCreateViewModel(
    private val superSetRepository: SuperSetRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseAutofillRepository: ExerciseAutofillRepository,
    private val appSettings: AppSettings,
    private val trainingRepository: TrainingRepository
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val exerciseInfoLiveDate = superSetRepository.currentExerciseInSuperSetFlow.asLiveData()

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

    fun deletedExercise(exercise: Exercise) {
        launch { exerciseRepository.deleteExercise(exercise) }
    }

    fun createSuperSet(idSuperSet: Long) {
        launch {
            superSetRepository.updateFlagVisibilitySuperSet(idSuperSet)
        }
    }

    fun getTraining(): Training {
        return runBlocking {
            trainingRepository.getTrainingById(appSettings.getIdTraining())
        }
    }


}