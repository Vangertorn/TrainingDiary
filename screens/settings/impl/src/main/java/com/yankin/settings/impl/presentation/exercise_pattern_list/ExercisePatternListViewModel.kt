package com.yankin.settings.impl.presentation.exercise_pattern_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.coroutine.launchInJob
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.settings.impl.navigation.ExercisePatternCreateDialogParams
import com.yankin.settings.impl.presentation.exercise_pattern_list.mappers.toExercisePatternListUiState
import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListEvent
import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListStateModel
import com.yankin.settings.impl.presentation.exercise_pattern_list.models.ExercisePatternListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class ExercisePatternListViewModel @Inject constructor(
    getCurrentExercisePatternStreamUseCase: GetCurrentExercisePatternStreamUseCase,
) : ViewModel() {

    private val exercisePatternListState = MutableStateFlow(
        ExercisePatternListStateModel(exercisePatternList = listOf())
    )
    private val exercisePatternListEventState: MutableStateFlow<ExercisePatternListEvent> =
        MutableStateFlow(ExercisePatternListEvent.Default)

    init {
        getCurrentExercisePatternStreamUseCase.invoke()
            .onEach { exercisePatternDomainList ->
                exercisePatternListState.update { stateModel ->
                    stateModel.copy(exercisePatternList = exercisePatternDomainList)
                }
            }
            .launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getExercisePatternListUiStream(): Flow<ExercisePatternListUiState> =
        exercisePatternListState.map { stateModel ->
            stateModel.toExercisePatternListUiState()
        }

    fun getExercisePatternListEventStream(): Flow<ExercisePatternListEvent> = exercisePatternListEventState
        .filterNot { event -> event is ExercisePatternListEvent.Default }

    fun onExercisePatternClick(exercisePatternId: Long) {
        exercisePatternListEventState.value = ExercisePatternListEvent.NavigateToCreateExercisePattern(
            params = ExercisePatternCreateDialogParams(exercisePatternId = exercisePatternId)
        )
    }

    fun onEventHandle() {
        exercisePatternListEventState.value = ExercisePatternListEvent.Default
    }
}
