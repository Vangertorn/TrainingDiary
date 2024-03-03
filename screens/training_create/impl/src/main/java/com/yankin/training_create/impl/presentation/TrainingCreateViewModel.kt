package com.yankin.training_create.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.date.DateFormatter
import com.yankin.training_exercises.api.navigation.TrainingExercisesParams
import com.yankin.membership.api.usecases.AddTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.GetActiveMembershipStreamUseCase
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupStreamUseCase
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training.api.usecases.SaveTrainingUseCase
import com.yankin.training.api.usecases.UpdateTrainingUseCase
import com.yankin.training_create.impl.navigation.TrainingCreateParcelableParams
import com.yankin.training_create.impl.presentation.mappers.toTrainingCreateUiState
import com.yankin.training_create.impl.presentation.models.TrainingCreateEvent
import com.yankin.training_create.impl.presentation.models.TrainingCreateStateModel
import com.yankin.training_create.impl.presentation.models.TrainingCreateUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import java.util.Date

internal class TrainingCreateViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getAllMuscleGroupListUseCase: GetAllMuscleGroupListUseCase,
    getAllMuscleGroupStreamUseCase: GetAllMuscleGroupStreamUseCase,
    private val saveTrainingUseCase: SaveTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    private val getTrainingByIdUseCase: GetTrainingByIdUseCase,
    private val addTrainingIdFromMembershipUseCase: AddTrainingIdFromMembershipUseCase,
    private val getActiveMembershipStreamUseCase: GetActiveMembershipStreamUseCase,
    @Assisted private val params: TrainingCreateParcelableParams,
) : ViewModel() {

    private val trainingCreateState = MutableStateFlow(
        TrainingCreateStateModel(
            muscleGroupList = emptyList(),
            selectedMuscleGroupIdList = savedStateHandle.get<IntArray>(KEY_SELECTED_MUSCLE_GROUP_ID_LIST)
                ?.toList() ?: emptyList(),
            currentTraining = null,
            selectedDate = savedStateHandle.get<String>(KEY_TRAINING_DATE) ?: "",
            weight = savedStateHandle.get<String>(KEY_TRAINING_WEIGHT) ?: "",
            comment = savedStateHandle.get<String>(KEY_TRAINING_COMMENT) ?: "",
            membership = null,
        )
    )

    private val trainingCreateEventState: MutableStateFlow<TrainingCreateEvent> =
        MutableStateFlow(TrainingCreateEvent.Default)

    init {
        getAllMuscleGroupStreamUseCase.invoke()
            .onEach { muscleGroupList ->
                trainingCreateState.update { stateModel -> stateModel.copy(muscleGroupList = muscleGroupList) }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            val membership = getActiveMembershipStreamUseCase.invoke().first()
            params.trainingId?.let { trainingId ->
                val training = getTrainingByIdUseCase.invoke(trainingId)
                trainingCreateState.update { stateModel ->
                    stateModel.copy(
                        currentTraining = training,
                        weight = training.getWeight(),
                        comment = training.getComment(),
                        selectedMuscleGroupIdList = training.getSelectedMuscleGroup(),
                        selectedDate = training.getDateString(),
                        membership = membership,
                    )
                }
            }?:run{
                trainingCreateState.update { stateModel -> stateModel.copy(membership = membership) }
            }
        }
    }

    fun getTrainingCreateUiStream(): Flow<TrainingCreateUiState> = trainingCreateState.map { stateModel ->
        stateModel.toTrainingCreateUiState()
    }

    fun getTrainingCreateEventStream(): Flow<TrainingCreateEvent> = trainingCreateEventState
        .filterNot { event -> event is TrainingCreateEvent.Default }

    fun onSaveClick() {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            trainingCreateState.value.currentTraining?.let { currentTraining ->
                updateTrainingUseCase.invoke(
                    currentTraining.copy(
                        date = trainingCreateState.value.selectedDate,
                        comment = trainingCreateState.value.comment,
                        weight = trainingCreateState.value.weight,
                        muscleGroups = trainingCreateState.value.selectedMuscleGroupIdList.getString(),
                        selectedMuscleGroup = trainingCreateState.value.selectedMuscleGroupIdList.toMutableList(),
                    )
                )
                trainingCreateEventState.value = TrainingCreateEvent.NavigateToExerciseList(
                    params = TrainingExercisesParams(trainingId = currentTraining.id)
                )
            } ?: run {
                val trainingId = saveTrainingUseCase.invoke(
                    TrainingDomain(
                        date = trainingCreateState.value.selectedDate,
                        comment = trainingCreateState.value.comment,
                        weight = trainingCreateState.value.weight,
                        muscleGroups = trainingCreateState.value.selectedMuscleGroupIdList.getString(),
                        selectedMuscleGroup = trainingCreateState.value.selectedMuscleGroupIdList.toMutableList(),
                    )
                )
                addTrainingIdFromMembershipUseCase.invoke(
                    trainingId = trainingId, membershipId = trainingCreateState.value.membership?.id
                )
                trainingCreateEventState.value = TrainingCreateEvent.Back
            }
        }
    }

    fun onEventHandle() {
        trainingCreateEventState.value = TrainingCreateEvent.Default
    }

    fun onCommentChange(commentValue: CharSequence) {
        trainingCreateState.update { stateModel ->
            stateModel.copy(comment = commentValue.toString()).also { updatedStateModel ->
                savedStateHandle[KEY_TRAINING_COMMENT] = updatedStateModel.comment
            }
        }
    }

    fun onWeightChange(weightValue: CharSequence) {
        trainingCreateState.update { stateModel ->
            stateModel.copy(weight = weightValue.toString()).also { updatedStateModel ->
                savedStateHandle[KEY_TRAINING_WEIGHT] = updatedStateModel.weight
            }
        }
    }

    fun onDateChange(date: Date) {
        trainingCreateState.update { stateModel ->
            stateModel.copy(
                selectedDate = DateFormatter.dateToString(
                    date = date,
                    dateFormat = DateFormatter.DD_POINT_MM_POINT_YY
                )
            ).also { updatedStateModel ->
                savedStateHandle[KEY_TRAINING_DATE] = updatedStateModel.selectedDate
            }
        }
    }

    fun onMuscleGroupClick(muscleGroupId: Long) {
        val resultList = trainingCreateState.value.selectedMuscleGroupIdList.toMutableList()
        if (!resultList.removeIf { id -> id == muscleGroupId.toInt() }) {
            resultList.add(muscleGroupId.toInt())
        }
        trainingCreateState.update { stateModel ->
            stateModel.copy(selectedMuscleGroupIdList = resultList).also {
                savedStateHandle[KEY_SELECTED_MUSCLE_GROUP_ID_LIST] = resultList.toIntArray()
            }
        }
    }

    private fun List<Int>.getString(): String {
        val stringBuilder = StringBuilder()
        val listMuscleGroups = runBlocking {
            getAllMuscleGroupListUseCase.invoke()
        }
        for (index in listMuscleGroups.indices) {
            if (index in this) {
                stringBuilder.append(listMuscleGroups[index].nameMuscleGroup)
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString().removeSuffix(", ")
    }

    private fun TrainingDomain.getWeight(): String =
        if (savedStateHandle.contains(KEY_TRAINING_WEIGHT)) trainingCreateState.value.weight else weight ?: ""

    private fun TrainingDomain.getComment(): String =
        if (savedStateHandle.contains(KEY_TRAINING_COMMENT)) trainingCreateState.value.comment else comment ?: ""

    private fun TrainingDomain.getDateString(): String =
        if (savedStateHandle.contains(KEY_TRAINING_DATE)) trainingCreateState.value.selectedDate else date

    private fun TrainingDomain.getSelectedMuscleGroup(): List<Int> =
        if (savedStateHandle.contains(KEY_SELECTED_MUSCLE_GROUP_ID_LIST)) {
            trainingCreateState.value.selectedMuscleGroupIdList
        } else {
            selectedMuscleGroup
        }

    companion object {

        private const val KEY_SELECTED_MUSCLE_GROUP_ID_LIST = "KEY_SELECTED_MUSCLE_GROUP_ID_LIST"
        private const val KEY_TRAINING_COMMENT = "KEY_TRAINING_COMMENT"
        private const val KEY_TRAINING_DATE = "KEY_TRAINING_DATE"
        private const val KEY_TRAINING_WEIGHT = "KEY_TRAINING_WEIGHT"
    }
}
