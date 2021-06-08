package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.TrainingDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.models.info.ExerciseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TrainingRepository(
    private val trainingDao: TrainingDao,
    private val appSettings: AppSettings,
) {

    val currentTrainingFlow: Flow<List<Training>> = trainingDao.getTrainingDeletedFalseFlow(false).map {
        it ?: listOf()
    }

    suspend fun saveTraining(training: Training) {
        withContext(Dispatchers.IO) {
            if (training.id == 0L) {
                val listPositions: MutableList<Int> =
                    trainingDao.getTrainingPositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                trainingDao.insertTraining(
                    Training(
                        date = training.date,
                        muscleGroups = training.muscleGroups,
                        comment = training.comment,
                        weight = training.weight,
                        position = listPositions[0] - 1
                    )
                )
            }
        }
    }


    suspend fun deletedTrainingTrue(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.deletedTrainingFlags(
                Training(
                    id = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position,
                    deleted = true
                )
            )
        }
    }
    suspend fun deletedTrainingFalse(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.deletedTrainingFlags(
                Training(
                    id = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position,
                    deleted = false
                )
            )
        }
    }

    suspend fun forgotIdTraining() {
        withContext(Dispatchers.IO) {
            appSettings.setIdTraining(-1)
        }
    }



    suspend fun getExercisesInfoByTrainingId(id: Long): List<ExerciseInfo> {
        return withContext(Dispatchers.IO) {
            return@withContext trainingDao.getExercisesInfoByTrainingId(id)
        }
    }

}