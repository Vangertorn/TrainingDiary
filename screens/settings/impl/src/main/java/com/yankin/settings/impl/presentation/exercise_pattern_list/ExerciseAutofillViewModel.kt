package com.yankin.settings.impl.presentation.exercise_pattern_list

import androidx.lifecycle.asLiveData
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.exercise_pattern.api.usecases.DeleteExercisePatternUseCase
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternUseCase
import com.yankin.settings.impl.presentation.exercisePattern
import com.yankin.settings.impl.presentation.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseAutofillViewModel @Inject constructor(
    getCurrentexercisePatternStreamUseCase: GetCurrentExercisePatternStreamUseCase,
    private val updateexercisePatternUseCase: UpdateExercisePatternUseCase,
    private val deleteexercisePatternUseCase: DeleteExercisePatternUseCase,
) : CoroutineViewModel() {

    val autoCompleteExerciseLiveData = getCurrentexercisePatternStreamUseCase.invoke().asLiveData()

    fun updateExerciseAutoFill(exercisePattern: exercisePattern) {
        launch {
            updateexercisePatternUseCase.invoke(exercisePattern.toDomain())
        }
    }

    fun deleteExerciseAutofill(exercisePattern: exercisePattern) {
        launch {
            deleteexercisePatternUseCase.invoke(exercisePattern.toDomain())
        }
    }
}
