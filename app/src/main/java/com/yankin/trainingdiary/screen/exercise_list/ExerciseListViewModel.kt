package com.yankin.trainingdiary.screen.exercise_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yankin.exercise.api.usecases.DeleteExerciseFalseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.api.usecases.SwitchExercisePositionUseCase
import com.yankin.training.api.usecases.ForgotIdTrainingUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val forgotIdTrainingUseCase: ForgotIdTrainingUseCase,
    private val superSetRepository: SuperSetRepository,
    private val deleteExerciseTrueUseCase: DeleteExerciseTrueUseCase,
    private val deleteExerciseFalseUseCase: DeleteExerciseFalseUseCase,
    private val switchExercisePositionUseCase: SwitchExercisePositionUseCase,
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    private val exerciseInfoFlow = exerciseRepository.currentExerciseFlow
    private val superSetDateFlow = superSetRepository.currentSuperSetDateFlow

    @ExperimentalCoroutinesApi
    val listLiveData: LiveData<List<ViewHolderTypes>> =
        combine(exerciseInfoFlow, superSetDateFlow) { exerciseInfo, superSetDao ->

            exerciseInfo +
                superSetDao
        }.asLiveData()

    @ExperimentalCoroutinesApi
    fun getExerciseFromPosition(position: Int): ViewHolderTypes {
        return listLiveData.value?.get(position)!!
    }

    fun deletedExerciseTrue(exercise: Exercise) {
        launch {
            deleteExerciseTrueUseCase.invoke(exercise.id)
        }
    }

    fun deletedExerciseFalse(exercise: Exercise) {
        launch {
            deleteExerciseFalseUseCase.invoke(exerciseId = exercise.id)
        }
    }

    fun deletedSuperSetTrue(superSet: SuperSet) {
        launch {
            superSetRepository.deletedSuperSetTrue(superSet)
        }
    }

    fun deletedSuperSetFalse(superSet: SuperSet) {
        launch {
            superSetRepository.deletedSuperSetFalse(superSet)
        }
    }

    fun forgotIdTraining() {
        launch {
            forgotIdTrainingUseCase.invoke()
        }
    }

    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }

    fun rememberIdSuperSet(superSet: SuperSet) {
        launch {
            appSettings.setSuperSetId(superSet.id)
        }
    }

    fun switchExercisePosition(exercise1: Exercise, exercise2: Exercise) {
        launch {
            switchExercisePositionUseCase.invoke(
                firstExerciseId = exercise1.id,
                firstExercisePosition = exercise2.position,
                secondExerciseId = exercise2.id,
                secondExercisePosition = exercise1.position
            )
        }
    }
}
