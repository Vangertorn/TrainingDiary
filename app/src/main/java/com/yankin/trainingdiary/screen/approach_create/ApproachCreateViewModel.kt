package com.yankin.trainingdiary.screen.approach_create

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.repository.ApproachRepository
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApproachCreateViewModel @Inject constructor(
    private val approachRepository: ApproachRepository,
    private val exerciseRepository: ExerciseRepository,
    appSettings: AppSettings,
    getCurrentExerciseNameAsStringStreamUseCase: GetCurrentExerciseNameAsStringStreamUseCase,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase
) :
    CoroutineViewModel() {

    @ExperimentalCoroutinesApi
    val approachLiveData = approachRepository.currentApproachFlow.asLiveData()
    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val autoCompleteExerciseLiveData = getCurrentExerciseNameAsStringStreamUseCase.invoke().asLiveData()

    fun addNewApproach(approach: Approach) {
        launch {
            approachRepository.saveApproach(approach)
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            approachRepository.deleteApproach(approach)
        }
    }

    fun updateExercise(exercise: Exercise) {
        launch {
            exerciseRepository.updateExercise(
                exercise
            )
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }
}
