package com.yankin.approach_create.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.approach_create.impl.navigation.SetCreateParcelableParams
import com.yankin.approach_create.impl.presentation.mappers.toExerciseCreateUiState
import com.yankin.approach_create.impl.presentation.models.SetCreateEvent
import com.yankin.approach_create.impl.presentation.models.SetCreateUiState
import com.yankin.approach_create.impl.presentation.state.ExerciseComment
import com.yankin.approach_create.impl.presentation.state.ExerciseName
import com.yankin.approach_create.impl.presentation.state.SetCreateStateOwner
import com.yankin.approach_create.impl.presentation.state.SetCreateStateOwnerImpl
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.exercise.api.usecases.GetExerciseByIdStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternByNameUseCase
import com.yankin.preferences.AppSettings
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.DeleteSetByIdUseCase
import com.yankin.set.api.usecases.GetSetListStreamUseCase
import com.yankin.set.api.usecases.SaveSetUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class SetCreateViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val params: SetCreateParcelableParams,
    private val appSettings: AppSettings,
    private val getCurrentExercisePatternStreamUseCase: GetCurrentExercisePatternStreamUseCase,
    private val saveSetUseCase: SaveSetUseCase,
    private val deleteSetByIdUseCase: DeleteSetByIdUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val resourceManager: ResourceManager,
    private val getExerciseListBySuperSetIdStreamUseCase: GetExerciseListBySuperSetIdStreamUseCase,
    private val getSetListStreamUseCase: GetSetListStreamUseCase,
    private val getExerciseByIdStreamUseCase: GetExerciseByIdStreamUseCase,
    private val updateExercisePatternByNameUseCase: UpdateExercisePatternByNameUseCase,
) : ViewModel(), SetCreateStateOwner by SetCreateStateOwnerImpl(savedStateHandle) {

    private val setCreateEventState: MutableStateFlow<SetCreateEvent> = MutableStateFlow(SetCreateEvent.Default)

    init {
        observeDefaultValues()
        observeExercisePatterns()
        when (params) {
            is SetCreateParcelableParams.SetCreate -> observeSingleExercise(params.exerciseId)
            is SetCreateParcelableParams.SuperSetCreate -> observeSuperSetExercise(params.superSetId)
        }
    }

    fun getSetCreateUiStream(): Flow<SetCreateUiState> = getStateStream().map { stateModel ->
        stateModel.toExerciseCreateUiState(resourceManager)
    }

    fun getSetCreateEventStream(): Flow<SetCreateEvent> = setCreateEventState
        .filterNot { event -> event is SetCreateEvent.Default }

    fun onEventHandle() {
        setCreateEventState.value = SetCreateEvent.Default
    }

    fun onAddSetClick() {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            saveSetUseCase.invoke(
                set = SetDomain(
                    id = 0, weight = weight, reps = reps, idExercise = selectedExerciseId ?: return@launchJob
                )
            )
        }
    }

    fun onSetClick(setId: Long) {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            deleteSetByIdUseCase.invoke(setId = setId)
        }
    }

    fun onExerciseClick(exerciseId: Long) {
        updateState(selectedExerciseId = exerciseId)
    }

    fun onCommentChange(commentValue: CharSequence) {
        updateState(exerciseComment = ExerciseComment(commentValue.toString()))
    }

    fun onExerciseNameChange(exerciseName: CharSequence) {
        updateState(exerciseName = ExerciseName(exerciseName.toString()))
    }

    fun onWeightUpClick() {
        updateState(weight = weight + 1)
    }

    fun onWeightDownClick() {
        updateState(weight = if (weight >= 1.0) weight - 1 else 0.0)
    }

    fun onRepsCountUpClick() {
        updateState(reps = reps + 1)
    }

    fun onRepsCountDownClick() {
        updateState(reps = if (reps >= 1) reps - 1 else 0)
    }

    fun onWeightChange(weight: CharSequence) {
        updateState(if (weight.isEmpty()) 0.0 else weight.toString().toDouble())
    }

    fun onRepsChange(reps: CharSequence) {
        updateState(if (reps.isEmpty()) 0 else reps.toString().toInt())
    }

    fun onUpdateExerciseClick() {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            if (exerciseNameByUser == "") {
                setCreateEventState.value = SetCreateEvent.ShowToast(
                    resourceManager.getString(CommonRString.exercise_name_is_empty)
                )
            } else {
                updateExerciseUseCase.invoke(
                    exerciseId = selectedExerciseId ?: return@launchJob,
                    exerciseName = exerciseNameByUser ?: exerciseName,
                    exerciseComment = exerciseComment
                )
                if (exerciseName != exerciseNameByUser && exerciseNameByUser != null) {
                    updateExercisePatternByNameUseCase.invoke(oldName = exerciseName, newName = exerciseNameByUser)
                }
            }
        }
    }

    private fun observeDefaultValues() {
        combine(
            appSettings.getRepsStream(),
            appSettings.getWeightStream()
        ) { defaultReps, defaultWeight ->
            updateState(defaultReps = defaultReps, defaultWeight = defaultWeight)
        }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    private fun observeExercisePatterns() {
        getCurrentExercisePatternStreamUseCase.invoke()
            .onEach { exercisePatternList ->
                updateState(exercisePatternList = exercisePatternList)
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    private fun observeSingleExercise(exerciseId: Long) {
        getExerciseByIdStreamUseCase.invoke(exerciseId)
            .onEach { exerciseDomain ->
                updateState(exerciseDomain = exerciseDomain)
                updateState(selectedExerciseId = exerciseDomain.id)
            }
            .flatMapLatest { exerciseDomain ->
                getSetListStreamUseCase.invoke(exerciseDomain.id)
                    .onEach { setDomainList ->
                        updateState(
                            setDomainList = setDomainList,
                            exerciseId = exerciseDomain.id
                        )
                    }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    private fun observeSuperSetExercise(superSetId: Long) {
        getExerciseListBySuperSetIdStreamUseCase.invoke(superSetId)
            .onEach { exerciseDomainList ->
                exerciseDomainList.forEach { exerciseDomain ->
                    updateState(exerciseDomain = exerciseDomain)
                }
                if (selectedExerciseId == null) updateState(selectedExerciseId = exerciseDomainList.first().id)

            }
            .flatMapMerge { exerciseDomainList ->
                combine(
                    exerciseDomainList
                        .map { exerciseDomain ->
                            getSetListStreamUseCase.invoke(exerciseDomain.id).onEach { setDomainList ->
                                updateState(
                                    setDomainList = setDomainList,
                                    exerciseId = exerciseDomain.id
                                )
                            }
                        }
                ) { setDomainListArray -> setDomainListArray }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }
}