package com.yankin.trainingdiary.repository

import com.yankin.storage.ExerciseStorage
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ExerciseRepository(
    private val exerciseStorage: ExerciseStorage,
    appSettings: AppSettings
) {

    @ExperimentalCoroutinesApi
    val currentExerciseFlow: Flow<List<ViewHolderTypes.ExerciseInfo>> =
        appSettings.idTrainingFlow().flatMapLatest { idTraining ->
            exerciseStorage.getExercisesInfoByTrainingIdAndFlagsFlow(idTraining, false)
                .map { list ->
                    list.map {
                        it.toModel()
                    }
                }
        }

    suspend fun saveExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            if (exercise.id == 0L) {
                val listPositions: MutableList<Int> =
                    exerciseStorage.getExercisePositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                exerciseStorage.insertExercise(
                    Exercise(
                        name = exercise.name,
                        idTraining = exercise.idTraining,
                        position = listPositions[0] - 1,
                        comment = exercise.comment,
                        idSet = exercise.idSet
                    ).toDomain()
                )
            }
        }
    }

    suspend fun deleteExercises() {
        withContext(Dispatchers.IO) {
            exerciseStorage.deletedExercisesByFlags(true)
        }
    }

    suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseStorage.deleteExercise(exercise.toDomain())
        }
    }

    suspend fun updateExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseStorage.updateExercise(exercise.toDomain())
        }
    }

    suspend fun deletedExerciseTrue(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseStorage.deletedExerciseFlags(
                Exercise(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    deleted = true,
                    comment = exercise.comment
                ).toDomain()
            )
        }
    }

    suspend fun deletedExerciseFalse(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseStorage.deletedExerciseFlags(
                Exercise(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    deleted = false,
                    comment = exercise.comment
                ).toDomain()
            )
        }
    }

    suspend fun switchExercisePosition(exercise1: Exercise, exercise2: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseStorage.switchExercisePositions(exercise1.toDomain(), exercise2.toDomain())
        }
    }

    suspend fun deleteEmptyExercise() {
        withContext(Dispatchers.IO) {
            exerciseStorage.deletedEmptyExercise("")
        }
    }
}
