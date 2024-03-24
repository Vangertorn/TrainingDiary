package com.yankin.workout_routines.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.approach_create.api.navigation.SetCreateParams
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.set.api.usecases.GetSetListStreamUseCase
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training_block.api.models.TrainingBlockDomain
import com.yankin.training_block.api.usecases.GetTrainingBlockListStreamUseCase
import com.yankin.training_block.api.usecases.SwitchTrainingBlockPositionUseCase
import com.yankin.training_block.api.usecases.UpdateTrainingBlockDeleteQueueUseCase
import com.yankin.workout_routines.impl.domain.ExerciseModel
import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.workout_routines.impl.navigation.WorkoutRoutinesParcelableParams
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId
import com.yankin.workout_routines.impl.presentation.mappers.toWorkoutRoutinesUiState
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesEvent
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesStateModel
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class WorkoutRoutinesViewModel @AssistedInject constructor(
    @Assisted private val params: WorkoutRoutinesParcelableParams,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val updateTrainingBlockDeleteQueueUseCase: UpdateTrainingBlockDeleteQueueUseCase,
    private val switchTrainingBlockPositionUseCase: SwitchTrainingBlockPositionUseCase,
    getTrainingBlockListStreamUseCase: GetTrainingBlockListStreamUseCase,
    private val getSetListStreamUseCase: GetSetListStreamUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val state = MutableStateFlow(
        WorkoutRoutinesStateModel(
            trainingId = params.trainingId,
            trainingDomain = null,
            trainingBlockList = listOf(),
        )
    )

    private val eventState: MutableStateFlow<WorkoutRoutinesEvent> =
        MutableStateFlow(WorkoutRoutinesEvent.Default)

    init {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            state.update { stateModel ->
                stateModel.copy(
                    trainingDomain = getTrainingByIdUseCase.invoke(params.trainingId)
                )
            }
        }
        getTrainingBlockListStreamUseCase.invoke(params.trainingId)
            .flatMapLatest { trainingBlockDomainList ->
                if (trainingBlockDomainList.isEmpty()) {
                    state.update { stateModel -> stateModel.copy(trainingBlockList = emptyList()) }
                }
                combine(
                    trainingBlockDomainList.map { trainingBlockDomain ->
                        when (trainingBlockDomain) {
                            is TrainingBlockDomain.SingleExercise -> getSingleExerciseFlow(trainingBlockDomain)
                            is TrainingBlockDomain.SuperSet -> getSuperSetFlow(trainingBlockDomain)
                        }
                    }
                ) { trainingBlockModelList: Array<TrainingBlockModel> ->
                    state.update { stateModel ->
                        stateModel.copy(trainingBlockList = trainingBlockModelList.toList())
                    }
                }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getUiStream(): Flow<WorkoutRoutinesUiState> = state.map { stateModel ->
        stateModel.toWorkoutRoutinesUiState(resourceManager)
    }

    fun getEventStream(): Flow<WorkoutRoutinesEvent> = eventState
        .filterNot { event -> event is WorkoutRoutinesEvent.Default }

    fun onEventHandle() {
        eventState.value = WorkoutRoutinesEvent.Default
    }

    fun onClickTrainingBlock(trainingBlockId: TrainingBlockId) {
        eventState.value = WorkoutRoutinesEvent.NavigateToSetCreate(
            SetCreateParams(trainingBlockId = trainingBlockId.value)
        )
    }

    fun onSwipeTrainingBlock(trainingBlockId: TrainingBlockId) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            updateTrainingBlockDeleteQueueUseCase.invoke(
                trainingBlockId = trainingBlockId.value,
                addToDeleteQueue = true
            )
            when (trainingBlockId) {
                is TrainingBlockId.SingleBlockId -> {
                    eventState.value = WorkoutRoutinesEvent.ShowSnackBar(
                        actionName = resourceManager.getString(CommonRString.undo),
                        message = resourceManager.getString(CommonRString.exercise_was_delete),
                        trainingBlockId = trainingBlockId.value
                    )
                }
                is TrainingBlockId.SuperSetId -> {
                    eventState.value = WorkoutRoutinesEvent.ShowSnackBar(
                        actionName = resourceManager.getString(CommonRString.undo),
                        message = resourceManager.getString(CommonRString.super_set_was_deleted),
                        trainingBlockId = trainingBlockId.value
                    )
                }
            }
        }
    }

    fun onUndoClick(trainingBlockId: Long) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            updateTrainingBlockDeleteQueueUseCase.invoke(trainingBlockId = trainingBlockId, addToDeleteQueue = false)
        }
    }

    fun onTrainingBlockPositionSwitch(firstTrainingBlockId: TrainingBlockId, secondTrainingBlockId: TrainingBlockId) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            switchTrainingBlockPositionUseCase.invoke(
                firstTrainingBlockId = firstTrainingBlockId.value,
                secondTrainingBlockId = secondTrainingBlockId.value
            )
        }
    }

    private fun getSingleExerciseFlow(
        trainingBlockDomain: TrainingBlockDomain.SingleExercise
    ): Flow<TrainingBlockModel.SingleExercise> {
        return getSetListStreamUseCase.invoke(trainingBlockDomain.exercise.id).map { setDomainList ->
            TrainingBlockModel.SingleExercise(
                exercise = ExerciseModel(
                    exercise = trainingBlockDomain.exercise,
                    setList = setDomainList
                ),
                trainingBlockDomain = trainingBlockDomain
            )
        }
    }

    private fun getSuperSetFlow(
        trainingBlockDomain: TrainingBlockDomain.SuperSet
    ): Flow<TrainingBlockModel.SuperSet> {
        return combine(
            trainingBlockDomain.exerciseList.map { exerciseDomain ->
                getSetListStreamUseCase.invoke(exerciseDomain.id).map { setDomainList ->
                    ExerciseModel(
                        exercise = exerciseDomain,
                        setList = setDomainList
                    )
                }
            }
        ) { exerciseModelArray ->
            TrainingBlockModel.SuperSet(
                exerciseList = exerciseModelArray.toList(),
                trainingBlockDomain = trainingBlockDomain
            )
        }
    }
}
