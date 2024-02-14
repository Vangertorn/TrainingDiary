package com.yankin.approach_create.impl.presentation.approach_create

import androidx.lifecycle.asLiveData
import com.yankin.set.api.usecases.DeleteSetUseCase
import com.yankin.set.api.usecases.GetCurrentSetStreamUseCase
import com.yankin.set.api.usecases.SaveSetUseCase
import com.yankin.approach_create.impl.presentation.Approach
import com.yankin.approach_create.impl.presentation.Exercise
import com.yankin.approach_create.impl.presentation.exercisePattern
import com.yankin.approach_create.impl.presentation.toDomain
import com.yankin.approach_create.impl.presentation.toModel
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternAsStringStreamUseCase
import com.yankin.exercise_pattern.api.usecases.SaveExercisePatternUseCase
import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.preferences.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApproachCreateViewModel @Inject constructor(
    appSettings: AppSettings,
    getCurrentexercisePatternAsStringStreamUseCase: GetCurrentExercisePatternAsStringStreamUseCase,
    private val saveexercisePatternUseCase: SaveExercisePatternUseCase,
    getCurrentSetStreamUseCase: GetCurrentSetStreamUseCase,
    private val saveSetUseCase: SaveSetUseCase,
    private val deleteSetUseCase: DeleteSetUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
) : CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val approachLiveData = getCurrentSetStreamUseCase.invoke().map { approachDomainList ->
        approachDomainList.map { approachDomain -> approachDomain.toModel() }
    }.asLiveData()
    val reoccurrencesLiveData = appSettings.getRepsStream().asLiveData()
    val weightLiveData = appSettings.getWeightStream().asLiveData()
    val autoCompleteExerciseLiveData = getCurrentexercisePatternAsStringStreamUseCase.invoke().asLiveData()

    fun addNewApproach(approach: Approach) {
        launch {
            saveSetUseCase.invoke(set = approach.toDomain())
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            deleteSetUseCase.invoke(set = approach.toDomain())
        }
    }

    fun updateExercise(exercise: Exercise) {
        launch {
            updateExerciseUseCase.invoke(exercise.toDomain())
        }
    }

    fun addNewExerciseAutofill(exercisePattern: exercisePattern) {
        launch {
            saveexercisePatternUseCase.invoke(exercisePattern.toDomain())
        }
    }
}
