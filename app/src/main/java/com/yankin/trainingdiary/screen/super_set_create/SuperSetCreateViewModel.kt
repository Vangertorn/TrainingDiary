package com.yankin.trainingdiary.screen.super_set_create

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseAutofill
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.repository.ExerciseAutofillRepository
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.repository.TrainingRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
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
            exerciseRepository.deleteEmptyExercise()
        }
    }

    fun getTraining(): Training {
        return runBlocking {
            trainingRepository.getTrainingById(appSettings.getIdTraining())
        }
    }
}