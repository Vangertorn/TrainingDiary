package com.yankin.settings.impl.presentation.exercise_pattern_create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.coroutine.launchJob
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.exercise_pattern.api.usecases.DeleteExercisePatternUseCase
import com.yankin.exercise_pattern.api.usecases.GetExercisePatternByIdUseCase
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternUseCase
import com.yankin.settings.impl.navigation.ExercisePatternCreateDialogParams
import com.yankin.settings.impl.presentation.exercise_pattern_create.mappers.toExercisePatternCreateUiState
import com.yankin.settings.impl.presentation.exercise_pattern_create.models.ExercisePatternCreateStateModel
import com.yankin.settings.impl.presentation.exercise_pattern_create.models.ExercisePatternCreateUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class ExercisePatternCreateViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getExercisePatternByIdUseCase: GetExercisePatternByIdUseCase,
    private val updateExercisePatternUseCase: UpdateExercisePatternUseCase,
    private val deleteExercisePatternUseCase: DeleteExercisePatternUseCase,
    @Assisted private val params: ExercisePatternCreateDialogParams,
) : ViewModel() {

    private val exercisePatternCreateState = MutableStateFlow(
        ExercisePatternCreateStateModel(
            exercisePattern = null,
            exerciseNameByUser = savedStateHandle.get<String>(KEY_EXERCISE_NAME) ?: ""
        )
    )

    init {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            params.exercisePatternId?.let { exercisePatternId ->
                val exercisePattern = getExercisePatternByIdUseCase.invoke(exercisePatternId)
                exercisePatternCreateState.update { stateModel ->
                    stateModel.copy(
                        exercisePattern = exercisePattern,
                        exerciseNameByUser = exercisePattern.getExerciseName(),
                    )
                }
            }
        }
    }

    fun getExercisePatternCreateUiStream(): Flow<ExercisePatternCreateUiState> =
        exercisePatternCreateState.map { stateModel ->
            stateModel.toExercisePatternCreateUiState()
        }

    fun onSaveExercisePatternClick() {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            exercisePatternCreateState.value.exercisePattern?.let { exercisePattern ->
                updateExercisePatternUseCase.invoke(
                    exercisePattern.copy(name = exercisePatternCreateState.value.exerciseNameByUser)
                )
            }

        }
    }

    fun onDeleteExercisePatternClick() {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            exercisePatternCreateState.value.exercisePattern?.let { exercisePattern ->
                deleteExercisePatternUseCase.invoke(exercisePattern.id)
            }
        }
    }

    fun onExerciseNameChange(exerciseName: CharSequence) {
        exercisePatternCreateState.update { stateModel ->
            stateModel.copy(exerciseNameByUser = exerciseName.toString()).also { updatedStateModel ->
                savedStateHandle[KEY_EXERCISE_NAME] = updatedStateModel.exerciseNameByUser
            }
        }
    }

    private fun ExercisePatternDomain.getExerciseName(): String =
        if (savedStateHandle.contains(KEY_EXERCISE_NAME)) exercisePatternCreateState.value.exerciseNameByUser else name

    companion object {

        private const val KEY_EXERCISE_NAME = "KEY_EXERCISE_NAME"
    }
}
