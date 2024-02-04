package com.yankin.training_list.impl.presentation

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.coroutine.launchInJob
import com.yankin.membership.api.navigation.MembershipParams
import com.yankin.membership.api.usecases.AddTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.DeleteTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.GetActiveMembershipStreamUseCase
import com.yankin.preferences.AppSettings
import com.yankin.training.api.usecases.DeleteTrainingFalseUseCase
import com.yankin.training.api.usecases.DeleteTrainingTrueUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingDescStreamUseCase
import com.yankin.training_list.impl.presentation.mappers.toTrainingListUiState
import com.yankin.training_list.impl.presentation.models.TrainingListEvent
import com.yankin.training_list.impl.presentation.models.TrainingListStateModel
import com.yankin.training_list.impl.presentation.models.TrainingListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
internal class TrainingListViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val deleteTrainingTrueUseCase: DeleteTrainingTrueUseCase,
    private val deleteTrainingFalseUseCase: DeleteTrainingFalseUseCase,
    getCurrentTrainingAscStreamUseCase: GetCurrentTrainingAscStreamUseCase,
    getCurrentTrainingDescStreamUseCase: GetCurrentTrainingDescStreamUseCase,
    private val getActiveMembershipStreamUseCase: GetActiveMembershipStreamUseCase,
    private val deleteTrainingIdFromMembershipUseCase: DeleteTrainingIdFromMembershipUseCase,
    private val addTrainingIdFromMembershipUseCase: AddTrainingIdFromMembershipUseCase,
) : CoroutineViewModel() {
    val trainingAscLiveData = getCurrentTrainingAscStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
    val trainingDescLiveData = getCurrentTrainingDescStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()

    private val trainingListState = MutableStateFlow(TrainingListStateModel(membership = null))
    private val trainingListEventState: MutableStateFlow<TrainingListEvent> =
        MutableStateFlow(TrainingListEvent.Default)

    init {
        getActiveMembershipStreamUseCase.invoke().onEach { membership ->
            trainingListState.update { stateModel -> stateModel.copy(membership = membership) }
        }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getTrainingListUiStream(): Flow<TrainingListUiState> = trainingListState.map { stateModel ->
        stateModel.toTrainingListUiState()
    }

    fun getTrainingCreateEventStream(): Flow<TrainingListEvent> = trainingListEventState
        .filterNot { event -> event is TrainingListEvent.Default }

    fun onEventHandle() {
        trainingListEventState.value = TrainingListEvent.Default
    }

    fun deletedTrainingTrue(training: Training) {
        launch {
            trainingListState.value.membership?.let { membership ->
                deleteTrainingIdFromMembershipUseCase.invoke(trainingId = training.id, membershipId = membership.id)
            }
            deleteTrainingTrueUseCase.invoke(training.toDomain())
        }
    }

    fun deletedTrainingFalse(training: Training) {
        launch {
            trainingListState.value.membership?.let { membership ->
                addTrainingIdFromMembershipUseCase.invoke(trainingId = training.id, membershipId = membership.id)
            }
            deleteTrainingFalseUseCase.invoke(training.toDomain())
        }
    }

    fun onMembershipClick() {
        trainingListState.value.membership?.let { membership ->
            trainingListEventState.value = TrainingListEvent.NavigateToEditMembership(MembershipParams(membership.id))
        } ?: run {
            trainingListEventState.value = TrainingListEvent.NavigateToCreateMembership
        }
    }

    fun getTrainingFromPosition(position: Int): Training? {
        return runBlocking {
            if (appSettings.orderAdded()) {
                return@runBlocking trainingAscLiveData.value?.get(position)
            } else {
                return@runBlocking trainingDescLiveData.value?.get(position)
            }
        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }
}
