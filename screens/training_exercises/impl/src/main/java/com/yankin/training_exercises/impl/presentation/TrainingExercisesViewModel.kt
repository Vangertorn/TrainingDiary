package com.yankin.training_exercises.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.approach_create.api.navigation.SetCreateParams
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.exercise.api.usecases.DeleteExerciseFalseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.api.usecases.GetTrainingExerciseListStreamUseCase
import com.yankin.exercise.api.usecases.SwitchExercisePositionUseCase
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.set.api.usecases.GetSetListStreamUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetFalseUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetTrueUseCase
import com.yankin.super_set.api.usecases.GetTrainingSuperSetListStreamUseCase
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training_exercises.impl.domain.TrainingExerciseModel
import com.yankin.training_exercises.impl.domain.TrainingSuperSetModel
import com.yankin.training_exercises.impl.navigation.TrainingExercisesParcelableParams
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId
import com.yankin.training_exercises.impl.presentation.mappers.toTrainingExercisesUiState
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesEvent
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesStateModel
import com.yankin.training_exercises.impl.presentation.models.TrainingExercisesUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class TrainingExercisesViewModel @AssistedInject constructor(
    @Assisted private val params: TrainingExercisesParcelableParams,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val deleteSuperSetFalseUseCase: DeleteSuperSetFalseUseCase,
    private val deleteSuperSetTrueUseCase: DeleteSuperSetTrueUseCase,
    private val deleteExerciseTrueUseCase: DeleteExerciseTrueUseCase,
    private val deleteExerciseFalseUseCase: DeleteExerciseFalseUseCase,
    private val switchExercisePositionUseCase: SwitchExercisePositionUseCase,
    getTrainingExerciseListStreamUseCase: GetTrainingExerciseListStreamUseCase,
    getTrainingSuperSetListStreamUseCase: GetTrainingSuperSetListStreamUseCase,
    private val getSetListStreamUseCase: GetSetListStreamUseCase,
    private val getExerciseListBySuperSetIdStreamUseCase: GetExerciseListBySuperSetIdStreamUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val state = MutableStateFlow(
        TrainingExercisesStateModel(
            trainingId = params.trainingId,
            trainingDomain = null,
            exerciseList = listOf(),
            supersetList = listOf(),
        )
    )

    private val eventState: MutableStateFlow<TrainingExercisesEvent> =
        MutableStateFlow(TrainingExercisesEvent.Default)

    init {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            state.update { stateModel ->
                stateModel.copy(
                    trainingDomain = getTrainingByIdUseCase.invoke(params.trainingId)
                )
            }
        }
        getTrainingExerciseListStreamUseCase.invoke(params.trainingId)
            .flatMapLatest { exerciseDomainList ->
                if (exerciseDomainList.isEmpty()) {
                    state.update { stateModel -> stateModel.copy(exerciseList = emptyList()) }
                }
                combine(
                    exerciseDomainList
                        .map { exerciseDomain ->
                            getSetListStreamUseCase.invoke(exerciseDomain.id)
                                .map { setDomainList ->
                                    TrainingExerciseModel(exercise = exerciseDomain, setList = setDomainList)
                                }
                        }
                ) { trainingExerciseModelList: Array<TrainingExerciseModel> ->
                    state.update { stateModel ->
                        stateModel.copy(
                            exerciseList = trainingExerciseModelList.toList()
                        )
                    }
                }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
        getTrainingSuperSetListStreamUseCase.invoke(params.trainingId)
            .flatMapLatest { superSetDomainList ->
                if (superSetDomainList.isEmpty()) {
                    state.update { stateModel -> stateModel.copy(supersetList = emptyList()) }
                }
                combine(
                    superSetDomainList
                        .map { superSetDomain ->
                            getExerciseListBySuperSetIdStreamUseCase.invoke(superSetDomain.id)
                                .flatMapLatest { exerciseDomainList ->
                                    combine(
                                        exerciseDomainList
                                            .map { exerciseDomain ->
                                                getSetListStreamUseCase.invoke(exerciseDomain.id)
                                                    .map { setDomainList ->
                                                        TrainingExerciseModel(
                                                            exercise = exerciseDomain,
                                                            setList = setDomainList
                                                        )
                                                    }
                                            }
                                    ) { trainingExerciseModelList: Array<TrainingExerciseModel> ->
                                        TrainingSuperSetModel(
                                            exerciseList = trainingExerciseModelList.toList(),
                                            superSet = superSetDomain
                                        )
                                    }
                                }
                        }
                )
                { trainingSuperSetModelList: Array<TrainingSuperSetModel> ->
                    state.update { stateModel ->
                        stateModel.copy(
                            supersetList = trainingSuperSetModelList.toList()
                        )
                    }
                }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getUiStream(): Flow<TrainingExercisesUiState> = state.map { stateModel ->
        stateModel.toTrainingExercisesUiState(resourceManager)
    }

    fun getEventStream(): Flow<TrainingExercisesEvent> = eventState
        .filterNot { event -> event is TrainingExercisesEvent.Default }

    fun onEventHandle() {
        eventState.value = TrainingExercisesEvent.Default
    }

    fun onClickTrainingExercise(trainingExerciseId: TrainingExerciseId) {
        when (trainingExerciseId) {
            is TrainingExerciseId.SingleExerciseId -> {
                eventState.value = TrainingExercisesEvent.NavigateToSetCreate(
                    SetCreateParams(exerciseId = trainingExerciseId.value, superSetId = null)
                )
            }
            is TrainingExerciseId.SuperSetId -> {
                eventState.value = TrainingExercisesEvent.NavigateToSetCreate(
                    SetCreateParams(exerciseId = null, superSetId = trainingExerciseId.value)
                )
            }
        }
    }

    fun onSwipeTrainingExercise(trainingExerciseId: TrainingExerciseId) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            when (trainingExerciseId) {
                is TrainingExerciseId.SingleExerciseId -> {
                    deleteExerciseTrueUseCase.invoke(trainingExerciseId.value)
                    eventState.value = TrainingExercisesEvent.ShowSnackBar(
                        actionName = resourceManager.getString(CommonRString.undo),
                        message = resourceManager.getString(CommonRString.exercise_was_delete),
                        trainingExerciseId = trainingExerciseId
                    )
                }
                is TrainingExerciseId.SuperSetId -> {
                    deleteSuperSetTrueUseCase.invoke(trainingExerciseId.value)
                    eventState.value = TrainingExercisesEvent.ShowSnackBar(
                        actionName = resourceManager.getString(CommonRString.undo),
                        message = resourceManager.getString(CommonRString.super_set_was_deleted),
                        trainingExerciseId = trainingExerciseId
                    )
                }
            }
        }
    }

    fun onUndoClick(trainingExerciseId: TrainingExerciseId) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            when (trainingExerciseId) {
                is TrainingExerciseId.SingleExerciseId -> deleteExerciseFalseUseCase.invoke(trainingExerciseId.value)
                is TrainingExerciseId.SuperSetId -> deleteSuperSetFalseUseCase.invoke(trainingExerciseId.value)
            }
        }
    }

    //    fun switchExercisePosition(exercise1: Exercise, exercise2: Exercise) {
    //        launch {
    //            switchExercisePositionUseCase.invoke(
    //                firstExerciseId = exercise1.id,
    //                firstExercisePosition = exercise2.position,
    //                secondExerciseId = exercise2.id,
    //                secondExercisePosition = exercise1.position
    //            )
    //        }
    //    }
}
