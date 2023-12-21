package com.yankin.trainingdiary.screen.super_set_create

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SuperSetCreateViewModel @Inject constructor(
    private val superSetRepository: SuperSetRepository,
    private val exerciseRepository: ExerciseRepository,
    private val appSettings: AppSettings,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val exerciseInfoLiveDate = superSetRepository.currentExerciseInSuperSetFlow.asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            exerciseRepository.saveExercise(exercise)
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
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
            getTrainingByIdUseCase.invoke(appSettings.getIdTraining()).toModel()
        }
    }
}
