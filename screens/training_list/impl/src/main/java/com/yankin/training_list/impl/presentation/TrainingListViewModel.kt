package com.yankin.training_list.impl.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.coroutine.launchInJob
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
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class TrainingListViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val deleteTrainingTrueUseCase: DeleteTrainingTrueUseCase,
    private val deleteTrainingFalseUseCase: DeleteTrainingFalseUseCase,
    getCurrentTrainingAscStreamUseCase: GetCurrentTrainingAscStreamUseCase,
    getCurrentTrainingDescStreamUseCase: GetCurrentTrainingDescStreamUseCase,
    private val getActiveMembershipStreamUseCase: GetActiveMembershipStreamUseCase,
) : CoroutineViewModel() {
    val trainingAscLiveData = getCurrentTrainingAscStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
    val trainingDescLiveData = getCurrentTrainingDescStreamUseCase.invoke().map { it.map { it.toModel() } }.asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val numberTrainingLiveData = appSettings.numberOfTrainingSessionsFlow().asLiveData()
    val numberLeftDaysLiveData = appSettings.leftDaysFlow().asLiveData()

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
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!! >= monthFormatter.parse(appSettings.getDateCreatedTicket())) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() + 1)
                }
            }

            deleteTrainingTrueUseCase.invoke(training.toDomain())
        }
    }

    fun onMembershipClick() {
        trainingListState.value.membership?.let { membership ->
            trainingListEventState.value = TrainingListEvent.NavigateToEditMembership(membership.id)
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

    fun numberOfTrainingSessions(): Int {
        return runBlocking {
            return@runBlocking appSettings.getNumberOfTrainingSessions()
        }
    }

    fun subscriptionEndDate(): String {
        return runBlocking {
            return@runBlocking appSettings.getSubscriptionEndDate()
        }
    }

    fun deletedTrainingFalse(training: Training) {

        launch {
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!!
                    >= monthFormatter.parse(appSettings.getDateCreatedTicket())
                ) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() - 1)
                }
            }

            deleteTrainingFalseUseCase.invoke(training.toDomain())
        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val monthFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    }
}
