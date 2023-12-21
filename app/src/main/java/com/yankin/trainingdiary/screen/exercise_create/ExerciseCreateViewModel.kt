package com.yankin.trainingdiary.screen.exercise_create

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ExerciseCreateViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings,
    getCurrentExerciseNameAsStringStreamUseCase: GetCurrentExerciseNameAsStringStreamUseCase,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase,
) : CoroutineViewModel() {

    val autoCompleteExerciseStringLiveData = getCurrentExerciseNameAsStringStreamUseCase.invoke().asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            exerciseRepository.saveExercise(exercise)
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }

    fun saveSuperSet(superSet: SuperSet, exercise: Exercise, emptyExercise: Exercise): Long {
        return runBlocking {
            superSetRepository.saveSuperSet(superSet, exercise, emptyExercise)
        }
    }

    fun saveSuperSetId(id: Long) {
        launch {
            appSettings.setSuperSetId(id)
        }
    }
}
