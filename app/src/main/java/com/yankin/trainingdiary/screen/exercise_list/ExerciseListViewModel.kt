package com.yankin.trainingdiary.screen.exercise_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yankin.approach.api.usecases.GetApproachListUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseFalseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.api.usecases.GetCurrentExerciseListStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdUseCase
import com.yankin.exercise.api.usecases.SwitchExercisePositionUseCase
import com.yankin.preferences.AppSettings
import com.yankin.super_set.api.usecases.DeleteSuperSetFalseUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetTrueUseCase
import com.yankin.super_set.api.usecases.GetCurrentSuperSetListStreamUseCase
import com.yankin.training.api.usecases.ForgotIdTrainingUseCase
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val forgotIdTrainingUseCase: ForgotIdTrainingUseCase,
    private val deleteSuperSetFalseUseCase: DeleteSuperSetFalseUseCase,
    private val deleteSuperSetTrueUseCase: DeleteSuperSetTrueUseCase,
    private val deleteExerciseTrueUseCase: DeleteExerciseTrueUseCase,
    private val deleteExerciseFalseUseCase: DeleteExerciseFalseUseCase,
    private val switchExercisePositionUseCase: SwitchExercisePositionUseCase,
    private val getCurrentExerciseListStreamUseCase: GetCurrentExerciseListStreamUseCase,
    private val getCurrentSuperSetListStreamUseCase: GetCurrentSuperSetListStreamUseCase,
    private val getApproachListUseCase: GetApproachListUseCase,
    private val getExerciseListBySuperSetIdUseCase: GetExerciseListBySuperSetIdUseCase,
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    private val exerciseInfoFlow: Flow<List<ViewHolderTypes.ExerciseInfo>> =
        getCurrentExerciseListStreamUseCase.invoke()
            .map { exerciseDomainList ->
                exerciseDomainList.map { exerciseDomain ->
                    ViewHolderTypes.ExerciseInfo(
                        exercise = exerciseDomain.toModel(),
                        approaches = getApproachListUseCase.invoke(exerciseDomain.id).map { approachDomain ->
                            approachDomain.toModel()
                        }
                    )
                }
            }
    private val superSetDateFlow: Flow<List<ViewHolderTypes.SuperSetDate>> =
        getCurrentSuperSetListStreamUseCase.invoke()
            .map { superSetDomainList ->
                superSetDomainList.map { superSetDomain ->
                    ViewHolderTypes.SuperSetDate(
                        superSet = superSetDomain.toModel(),
                        exercise = getExerciseListBySuperSetIdUseCase.invoke(superSetDomain.id).map { exerciseDomain ->
                            ViewHolderTypes.ExerciseInfo(
                                exercise = exerciseDomain.toModel(),
                                approaches = getApproachListUseCase.invoke(exerciseDomain.id).map { approachDomain ->
                                    approachDomain.toModel()
                                }
                            )
                        }
                    )
                }
            }

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
            deleteSuperSetTrueUseCase.invoke(superSetId = superSet.id)
        }
    }

    fun deletedSuperSetFalse(superSet: SuperSet) {
        launch {
            deleteSuperSetFalseUseCase.invoke(superSetId = superSet.id)
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
