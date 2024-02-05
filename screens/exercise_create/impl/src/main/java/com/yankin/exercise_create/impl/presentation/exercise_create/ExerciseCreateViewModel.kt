package com.yankin.exercise_create.impl.presentation.exercise_create

import androidx.lifecycle.asLiveData
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternAsStringStreamUseCase
import com.yankin.exercise_pattern.api.usecases.SaveExercisePatternUseCase
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.exercise_create.impl.presentation.Exercise
import com.yankin.exercise_create.impl.presentation.exercisePattern
import com.yankin.exercise_create.impl.presentation.SuperSet
import com.yankin.exercise_create.impl.presentation.toDomain
import com.yankin.preferences.AppSettings
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ExerciseCreateViewModel @Inject constructor(
    private val saveExerciseUseCase: SaveExerciseUseCase,
    private val appSettings: AppSettings,
    getCurrentexercisePatternAsStringStreamUseCase: GetCurrentExercisePatternAsStringStreamUseCase,
    private val saveexercisePatternUseCase: SaveExercisePatternUseCase,
    private val saveSuperSetUseCase: SaveSuperSetUseCase,
) : CoroutineViewModel() {

    val autoCompleteExerciseStringLiveData = getCurrentexercisePatternAsStringStreamUseCase.invoke().asLiveData()

    fun addNewExercise(exercise: Exercise) {
        launch {
            saveExerciseUseCase.invoke(exercise.toDomain())
        }
    }

    fun addNewExerciseAutofill(exercisePattern: exercisePattern) {
        launch {
            saveexercisePatternUseCase.invoke(exercisePattern.toDomain())
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
