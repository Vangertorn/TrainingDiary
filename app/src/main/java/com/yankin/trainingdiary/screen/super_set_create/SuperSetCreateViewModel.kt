package com.yankin.trainingdiary.screen.super_set_create

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.exercise.api.usecases.DeleteEmptyExerciseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.preferences.AppSettings
import com.yankin.super_set.api.usecases.UpdateSuperSetVisibleUseCase
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SuperSetCreateViewModel @Inject constructor(
    private val saveExerciseUseCase: SaveExerciseUseCase,
    private val deleteExerciseTrueUseCase: DeleteExerciseTrueUseCase,
    private val deleteEmptyExerciseUseCase: DeleteEmptyExerciseUseCase,
    private val appSettings: AppSettings,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val updateSuperSetVisibleUseCase: UpdateSuperSetVisibleUseCase,
    private val getExerciseListBySuperSetIdStreamUseCase: GetExerciseListBySuperSetIdStreamUseCase,
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val exerciseInfoLiveDate = appSettings.idSuperSetFlow().flatMapLatest { superSetId ->
        getExerciseListBySuperSetIdStreamUseCase.invoke(superSetId).map { exerciseDomainList ->
            exerciseDomainList.map { exerciseDomain ->
                exerciseDomain.toModel()
            }
        }
    }
        .asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            saveExerciseUseCase.invoke(exercise.toDomain())
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }

    fun deletedExercise(exercise: Exercise) {
        launch { deleteExerciseTrueUseCase.invoke(exerciseId = exercise.id) }
    }

    fun createSuperSet(idSuperSet: Long) {
        launch {
            updateSuperSetVisibleUseCase.invoke(idSuperSet)
            deleteEmptyExerciseUseCase.invoke()
        }
    }

    fun getTraining(): Training {
        return runBlocking {
            getTrainingByIdUseCase.invoke(appSettings.getIdTraining()).toModel()
        }
    }
}
