package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.ExerciseDao
import com.example.trainingdiary.dao.database.TrainingDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.info.ExerciseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val trainingDao: TrainingDao,
    private val appSettings: AppSettings
)
{

    @ExperimentalCoroutinesApi
    val currentExerciseFlow: Flow<List<Exercise>> =
        appSettings.idTrainingFlow().flatMapLatest { idTraining ->
            trainingDao.getTrainingInfoFlow(idTraining).map {
                it?.exercises ?: emptyList()
            }
        }


    suspend fun saveExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.saveExercise(exercise)
        }
    }

    suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise((exercise))
        }
    }
}