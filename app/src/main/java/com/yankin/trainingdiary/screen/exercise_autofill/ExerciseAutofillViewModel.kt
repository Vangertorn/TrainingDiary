package com.yankin.trainingdiary.screen.exercise_autofill

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.DeleteExerciseNameUseCase
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameStreamUseCase
import com.yankin.exercese_name.api.usecases.UpdateExerciseNameUseCase
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseAutofillViewModel @Inject constructor(
    getCurrentExerciseNameStreamUseCase: GetCurrentExerciseNameStreamUseCase,
    private val updateExerciseNameUseCase: UpdateExerciseNameUseCase,
    private val deleteExerciseNameUseCase: DeleteExerciseNameUseCase,
) : CoroutineViewModel() {

    val autoCompleteExerciseLiveData = getCurrentExerciseNameStreamUseCase.invoke().asLiveData()

    fun updateExerciseAutoFill(exerciseName: ExerciseName) {
        launch {
            updateExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }

    fun deleteExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            deleteExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }
}
