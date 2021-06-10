package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.ExerciseDao
import com.example.trainingdiary.dao.database.TrainingDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.models.info.ExerciseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val trainingDao: TrainingDao,
    private val appSettings: AppSettings
) {

    @ExperimentalCoroutinesApi
    val currentExerciseFlow: Flow<List<ExerciseInfo>> =
        appSettings.idTrainingFlow().flatMapLatest { idTraining ->
            trainingDao.getExercisesInfoByTrainingIdAndFlagsFlow(idTraining, false)
        }


    suspend fun saveExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            if (exercise.id == 0L) {
                val listPositions: MutableList<Int> =
                    exerciseDao.getExercisePositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                exerciseDao.insertExercise(
                    Exercise(
                        name = exercise.name,
                        idTraining = exercise.idTraining,
                        position = listPositions[0] - 1,
                        comment = exercise.comment
                    )
                )
            }
        }
    }

    suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise((exercise))
        }
    }

    suspend fun updateExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.updateExercise(exercise)
        }
    }
    suspend fun getExerciseFromId(id: Long): Exercise{
        return withContext(Dispatchers.IO){
            exerciseDao.getExerciseFromId(id)
        }
    }

    suspend fun deletedExerciseTrue(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deletedExerciseFlags(
                Exercise(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    deleted = true,
                    comment = exercise.comment
                )
            )
        }
    }

    suspend fun deletedExerciseFalse(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deletedExerciseFlags(
                Exercise(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    deleted = false,
                    comment = exercise.comment
                )
            )
        }
    }
}