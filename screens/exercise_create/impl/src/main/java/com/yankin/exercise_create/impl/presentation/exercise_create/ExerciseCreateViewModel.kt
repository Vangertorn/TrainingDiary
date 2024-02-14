package com.yankin.exercise_create.impl.presentation.exercise_create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.exercise_create.impl.navigation.ExerciseCreateParcelableParams
import com.yankin.exercise_create.impl.presentation.exercise_create.mappers.toExerciseCreateUiState
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseCreateEvent
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseCreateStateModel
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseCreateUiState
import com.yankin.exercise_create.impl.presentation.exercise_create.models.ExerciseTemporary
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.exercise_pattern.api.usecases.SaveExercisePatternUseCase
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class ExerciseCreateViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val saveExerciseUseCase: SaveExerciseUseCase,
    private val getCurrentExercisePatternStreamUseCase: GetCurrentExercisePatternStreamUseCase,
    private val saveExercisePatternUseCase: SaveExercisePatternUseCase,
    private val saveSuperSetUseCase: SaveSuperSetUseCase,
    @Assisted private val params: ExerciseCreateParcelableParams,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val exerciseCreateState = MutableStateFlow(
        ExerciseCreateStateModel(
            exerciseName = savedStateHandle.get<String>(EXERCISE_NAME_KEY) ?: "",
            exerciseComment = savedStateHandle.get<String>(EXERCISE_COMMENT_KEY),
            exercisePatternList = listOf(),
            exerciseList = savedStateHandle.get<Array<ExerciseTemporary>>(TEMPORARY_EXERCISE_LIST_KEY)
                ?.toList() ?: emptyList(),
            selectedExerciseId = savedStateHandle.get<Int>(SELECTED_EXERCISE_ID_KEY),
        )
    )
    private val exerciseCreateEventState: MutableStateFlow<ExerciseCreateEvent> =
        MutableStateFlow(ExerciseCreateEvent.Default)

    init {
        getCurrentExercisePatternStreamUseCase.invoke()
            .onEach { exercisePatternList ->
                exerciseCreateState.update { stateModel -> stateModel.copy(exercisePatternList = exercisePatternList) }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getExerciseCreateUiStream(): Flow<ExerciseCreateUiState> = exerciseCreateState.map { stateModel ->
        stateModel.toExerciseCreateUiState(resourceManager)
    }

    fun getExerciseCreateEventStream(): Flow<ExerciseCreateEvent> = exerciseCreateEventState
        .filterNot { event -> event is ExerciseCreateEvent.Default }

    fun onEventHandle() {
        exerciseCreateEventState.value = ExerciseCreateEvent.Default
    }

    fun onCommentChange(commentValue: CharSequence?) {
        exerciseCreateState.value.selectedExerciseId?.let { selectedExerciseId ->
            exerciseCreateState.value.exerciseList.first { exercise ->
                exercise.id == selectedExerciseId
            }.copy(comment = commentValue.toString()).also { updatedExercise -> updatedExercise.updateStateModel() }
        } ?: run {
            exerciseCreateState.update { stateModel ->
                stateModel.copy(exerciseComment = commentValue.toString()).also { updatedStateModel ->
                    savedStateHandle[EXERCISE_COMMENT_KEY] = updatedStateModel.exerciseComment
                }
            }
        }
    }

    fun onExerciseNameChange(exerciseName: CharSequence) {
        exerciseCreateState.value.selectedExerciseId?.let { selectedExerciseId ->
            exerciseCreateState.value.exerciseList.first { exercise ->
                exercise.id == selectedExerciseId
            }.copy(name = exerciseName.toString()).also { updatedExercise -> updatedExercise.updateStateModel() }
        } ?: run {
            exerciseCreateState.update { stateModel ->
                stateModel.copy(exerciseName = exerciseName.toString()).also { updatedStateModel ->
                    savedStateHandle[EXERCISE_NAME_KEY] = updatedStateModel.exerciseName
                }
            }
        }
    }

    fun onExerciseClick(exerciseId: Int) {
        exerciseCreateState.update { stateModel ->
           val exercise =  stateModel.exerciseList.first { exerciseTemporary -> exerciseTemporary.id == exerciseId }
            stateModel.copy(
                selectedExerciseId = exerciseId,
                exerciseName = exercise.name,
                exerciseComment = exercise.comment,
            ).also { updatedStateModel ->
                savedStateHandle[SELECTED_EXERCISE_ID_KEY] = updatedStateModel.selectedExerciseId
            }
        }
    }

    fun addExerciseClick() {
        exerciseCreateState.update { stateModel ->
            stateModel.copy(
                selectedExerciseId = null,
                exerciseName = savedStateHandle[EXERCISE_NAME_KEY]?:"",
                exerciseComment = savedStateHandle[EXERCISE_COMMENT_KEY],
            ).also { updatedStateModel ->
                savedStateHandle[SELECTED_EXERCISE_ID_KEY] = updatedStateModel.selectedExerciseId
            }
        }
    }

    fun onActionButtonClick() {
        if (exerciseCreateState.value.exerciseName.isEmpty()) {
            exerciseCreateEventState.value =
                ExerciseCreateEvent.ShowToast(resourceManager.getString(CommonRString.exercise_name_is_empty))
            return
        }
        exerciseCreateState.value.selectedExerciseId?.let { selectedExerciseId ->
            exerciseCreateState.update { stateModel ->
                stateModel.copy(
                    exerciseList = stateModel.removeTemporaryExercise(selectedExerciseId)
                ).also { updatedStateModel ->
                    savedStateHandle[TEMPORARY_EXERCISE_LIST_KEY] = updatedStateModel.exerciseList.toTypedArray()
                }
            }
        } ?: run {
            exerciseCreateState.update { stateModel ->
                stateModel.copy(
                    exerciseList = stateModel.addTemporaryExercise(),
                    exerciseName = "",
                    exerciseComment = null,
                ).also { updatedStateModel ->
                    savedStateHandle[TEMPORARY_EXERCISE_LIST_KEY] = updatedStateModel.exerciseList.toTypedArray()
                    savedStateHandle[EXERCISE_COMMENT_KEY] = updatedStateModel.exerciseComment
                    savedStateHandle[EXERCISE_NAME_KEY] = updatedStateModel.exerciseName
                }
            }
        }
    }

    fun onSaveButtonClick() {
        if (exerciseCreateState.value.exerciseName.isEmpty() && exerciseCreateState.value.exerciseList.isEmpty()) {
            exerciseCreateEventState.value =
                ExerciseCreateEvent.ShowToast(resourceManager.getString(CommonRString.exercise_name_is_empty))
            return
        }
        if (exerciseCreateState.value.exerciseList.isEmpty() || exerciseCreateState.value.exerciseList.size == 1) {
            viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                saveExerciseUseCase.invoke(
                    exercise = ExerciseDomain(
                        id = 0,
                        name = exerciseCreateState.value.exerciseName,
                        idTraining = params.trainingId,
                        position = 0,
                        comment = exerciseCreateState.value.exerciseComment,
                        deleted = false,
                        idSet = null,
                    )
                )
                saveExercisePatternUseCase.invoke(
                    exercisePattern = ExercisePatternDomain(id = 0, name = exerciseCreateState.value.exerciseName)
                )
                exerciseCreateEventState.value = ExerciseCreateEvent.Dismiss
            }
        } else {
            viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                val superSetId = saveSuperSetUseCase.invoke(
                    superSet = SuperSetDomain(
                        id = 0,
                        idTraining = params.trainingId,
                        deleted = false,
                        position = 0
                    )
                )
                exerciseCreateState.value.exerciseList.forEach { exerciseTemporary ->
                    saveExerciseUseCase.invoke(
                        exercise = ExerciseDomain(
                            id = 0,
                            name = exerciseTemporary.name,
                            idTraining = params.trainingId,
                            position = 0,
                            comment = exerciseTemporary.comment,
                            deleted = false,
                            idSet = superSetId,
                        )
                    )
                    saveExercisePatternUseCase.invoke(
                        exercisePattern = ExercisePatternDomain(id = 0, name = exerciseTemporary.name)
                    )
                }
                exerciseCreateEventState.value = ExerciseCreateEvent.Dismiss
            }
        }
    }

    private fun ExerciseCreateStateModel.removeTemporaryExercise(selectedExerciseId: Int): List<ExerciseTemporary> {
        val exerciseTemporary = exerciseList.first { exercise -> exercise.id == selectedExerciseId }
        val result = exerciseList.toMutableList()
        result.remove(exerciseTemporary)
        return result
    }

    private fun ExerciseCreateStateModel.addTemporaryExercise(): List<ExerciseTemporary> {
        val exerciseTemporary = ExerciseTemporary(
            id = exerciseList.size,
            name = exerciseName,
            comment = exerciseComment,
        )
        val result = exerciseList.toMutableList()
        result.add(exerciseTemporary)
        return result
    }

    private fun ExerciseTemporary.updateStateModel() {
        val updatedList = exerciseCreateState.value.exerciseList.map { exercise ->
            if (exercise.id == id) this else exercise
        }
        exerciseCreateState.update { stateModel ->
            stateModel.copy(exerciseList = updatedList).also { updatedStateModel ->
                savedStateHandle[TEMPORARY_EXERCISE_LIST_KEY] = updatedStateModel.exerciseList.toTypedArray()
            }
        }
    }

    companion object {
        private const val EXERCISE_NAME_KEY = "EXERCISE_NAME_KEY"
        private const val EXERCISE_COMMENT_KEY = "EXERCISE_COMMENT_KEY"
        private const val TEMPORARY_EXERCISE_LIST_KEY = "TEMPORARY_EXERCISE_LIST_KEY"
        private const val SELECTED_EXERCISE_ID_KEY = "SELECTED_EXERCISE_ID_KEY"
    }
}