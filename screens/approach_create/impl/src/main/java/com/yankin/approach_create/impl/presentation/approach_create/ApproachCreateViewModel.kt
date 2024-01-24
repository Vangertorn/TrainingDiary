package com.yankin.approach_create.impl.presentation.approach_create

import androidx.lifecycle.asLiveData
import com.yankin.approach.api.usecases.DeleteApproachUseCase
import com.yankin.approach.api.usecases.GetCurrentApproachStreamUseCase
import com.yankin.approach.api.usecases.SaveApproachUseCase
import com.yankin.approach_create.impl.presentation.Approach
import com.yankin.approach_create.impl.presentation.Exercise
import com.yankin.approach_create.impl.presentation.ExerciseName
import com.yankin.approach_create.impl.presentation.toDomain
import com.yankin.approach_create.impl.presentation.toModel
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
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
    getCurrentExerciseNameAsStringStreamUseCase: GetCurrentExerciseNameAsStringStreamUseCase,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase,
    getCurrentApproachStreamUseCase: GetCurrentApproachStreamUseCase,
    private val saveApproachUseCase: SaveApproachUseCase,
    private val deleteApproachUseCase: DeleteApproachUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
) : CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val approachLiveData = getCurrentApproachStreamUseCase.invoke().map { approachDomainList ->
        approachDomainList.map { approachDomain -> approachDomain.toModel() }
    }.asLiveData()
    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val autoCompleteExerciseLiveData = getCurrentExerciseNameAsStringStreamUseCase.invoke().asLiveData()

    fun addNewApproach(approach: Approach) {
        launch {
            saveApproachUseCase.invoke(approach = approach.toDomain())
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            deleteApproachUseCase.invoke(approach = approach.toDomain())
        }
    }

    fun updateExercise(exercise: Exercise) {
        launch {
            updateExerciseUseCase.invoke(exercise.toDomain())
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }
}
