package com.yankin.trainingdiary.screen.exercise_create

import androidx.lifecycle.asLiveData
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.preferences.AppSettings
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseName
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ExerciseCreateViewModel @Inject constructor(
    private val saveExerciseUseCase: SaveExerciseUseCase,
    private val appSettings: AppSettings,
    getCurrentExerciseNameAsStringStreamUseCase: GetCurrentExerciseNameAsStringStreamUseCase,
    private val saveExerciseNameUseCase: SaveExerciseNameUseCase,
    private val saveSuperSetUseCase: SaveSuperSetUseCase,
) : CoroutineViewModel() {

    val autoCompleteExerciseStringLiveData = getCurrentExerciseNameAsStringStreamUseCase.invoke().asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            saveExerciseUseCase.invoke(exercise.toDomain())
        }
    }

    fun addNewExerciseAutofill(exerciseName: ExerciseName) {
        launch {
            saveExerciseNameUseCase.invoke(exerciseName.toDomain())
        }
    }

    fun saveSuperSet(superSet: SuperSet, exercise: Exercise, emptyExercise: Exercise): Long {
        return runBlocking {
            val superSetId = saveSuperSetUseCase.invoke(superSet.toDomain())
            saveExerciseUseCase.invoke(
                ExerciseDomain(
                    id = 0,
                    name = "",
                    idTraining = emptyExercise.idTraining,
                    position = 0,
                    comment = null,
                    deleted = false,
                    idSet = superSetId
                )
            )
            saveExerciseUseCase.invoke(
                ExerciseDomain(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    comment = exercise.comment,
                    deleted = exercise.deleted,
                    idSet = superSetId
                )
            )
            return@runBlocking superSetId
        }
    }

    fun saveSuperSetId(id: Long) {
        launch {
            appSettings.setSuperSetId(id)
        }
    }
}
