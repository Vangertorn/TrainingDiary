package com.yankin.training_list.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.membership.api.navigation.MembershipParams
import com.yankin.membership.api.usecases.AddTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.DeleteTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.GetActiveMembershipStreamUseCase
import com.yankin.preferences.AppSettings
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingDescStreamUseCase
import com.yankin.training.api.usecases.UpdateTrainingDeleteQueueUseCase
import com.yankin.training_list.impl.presentation.mappers.toTrainingListUiState
import com.yankin.training_list.impl.presentation.models.TrainingListEvent
import com.yankin.training_list.impl.presentation.models.TrainingListStateModel
import com.yankin.training_list.impl.presentation.models.TrainingListUiState
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class TrainingListViewModel @Inject constructor(
    appSettings: AppSettings,
    private val updateTrainingDeleteQueueUseCase: UpdateTrainingDeleteQueueUseCase,
    getCurrentTrainingAscStreamUseCase: GetCurrentTrainingAscStreamUseCase,
    getCurrentTrainingDescStreamUseCase: GetCurrentTrainingDescStreamUseCase,
    getActiveMembershipStreamUseCase: GetActiveMembershipStreamUseCase,
    private val deleteTrainingIdFromMembershipUseCase: DeleteTrainingIdFromMembershipUseCase,
    private val addTrainingIdFromMembershipUseCase: AddTrainingIdFromMembershipUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val trainingListState = MutableStateFlow(
        TrainingListStateModel(
            membership = null,
            trainings = emptyList(),
            isLastTrainUp = false,
            isScrollNeed = false,
        )
    )
    private val trainingListEventState: MutableStateFlow<TrainingListEvent> =
        MutableStateFlow(TrainingListEvent.Default)

    init {
        getActiveMembershipStreamUseCase.invoke().onEach { membership ->
            trainingListState.update { stateModel -> stateModel.copy(membership = membership) }
        }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
        appSettings.orderAddedFlow()
            .flatMapLatest { isLastTrainUp ->
                trainingListState.update { stateModel -> stateModel.copy(isLastTrainUp = isLastTrainUp) }
                if (isLastTrainUp) {
                    getCurrentTrainingAscStreamUseCase.invoke()
                } else {
                    getCurrentTrainingDescStreamUseCase.invoke()
                }
            }
            .onEach { trainings ->
                trainingListState.update { stateModel ->
                    stateModel.copy(
                        trainings = trainings,
                        isScrollNeed = stateModel.trainings!= trainings
                    )
                }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getTrainingListUiStream(): Flow<TrainingListUiState> = trainingListState.map { stateModel ->
        stateModel.toTrainingListUiState(resourceManager)
    }

    fun getTrainingCreateEventStream(): Flow<TrainingListEvent> = trainingListEventState
        .filterNot { event -> event is TrainingListEvent.Default }

    fun onEventHandle() {
        trainingListEventState.value = TrainingListEvent.Default
    }

    fun onSwipeTraining(trainingId: Long) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            trainingListState.value.membership?.let { membership ->
                deleteTrainingIdFromMembershipUseCase.invoke(trainingId = trainingId, membershipId = membership.id)
            }
            updateTrainingDeleteQueueUseCase.invoke(trainingId = trainingId, addToDeleteQueue = true)
        }
        trainingListEventState.value = TrainingListEvent.ShowSnackBar(
            actionName = resourceManager.getString(CommonRString.undo),
            message = resourceManager.getString(CommonRString.training_was_delete),
            trainingId = trainingId
        )
    }

    fun onUndoClick(trainingId: Long) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            trainingListState.value.membership?.let { membership ->
                addTrainingIdFromMembershipUseCase.invoke(trainingId = trainingId, membershipId = membership.id)
            }
            updateTrainingDeleteQueueUseCase.invoke(trainingId = trainingId, addToDeleteQueue = false)
        }
    }

    fun onTrainingClick(trainingId: Long) {
        trainingListEventState.value = TrainingListEvent.NavigateToWorkout(
            WorkoutRoutinesParams(trainingId = trainingId)
        )
    }

    fun onMembershipClick() {
        trainingListState.value.membership?.let { membership ->
            trainingListEventState.value = TrainingListEvent.NavigateToEditMembership(MembershipParams(membership.id))
        } ?: run {
            trainingListEventState.value = TrainingListEvent.NavigateToCreateMembership
        }
    }
}
