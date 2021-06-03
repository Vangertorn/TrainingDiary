package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.TrainingDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Training
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TrainingRepository(
    private val trainingDao: TrainingDao,
    private val appSettings: AppSettings
) {

    val currentTrainingFlow: Flow<List<Training>> = trainingDao.getTrainingFlow().map {
        it ?: listOf()
    }

    suspend fun saveTraining(training: Training) {
        withContext(Dispatchers.IO) {
            val trainings = trainingDao.getTrainings() ?: emptyList()
            trainingDao.updateTrainingTable(trainings.map {
                when {
                    training.position == 0 -> {
                        Training(
                            id = it.id,
                            date = it.date,
                            muscleGroups = it.muscleGroups,
                            comment = it.comment,
                            weight = it.weight,
                            position = it.position + 1
                        )
                    }
                    training.position > it.position -> {
                        Training(
                            id = it.id,
                            date = it.date,
                            muscleGroups = it.muscleGroups,
                            comment = it.comment,
                            weight = it.weight,
                            position = it.position
                        )
                    }
                    else -> {
                        Training(
                            id = it.id,
                            date = it.date,
                            muscleGroups = it.muscleGroups,
                            comment = it.comment,
                            weight = it.weight,
                            position = it.position + 1
                        )
                    }
                }
            })
            if (training.id == 0L) {
                trainingDao.insertTraining(training)
            } else {
                reSaveTraining(training)
            }
        }
    }



    suspend fun deleteTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.deleteTraining(training)
            val trainings = trainingDao.getTrainings() ?: emptyList()
            trainingDao.updateTrainingTable(trainings.map {
                if (it.position > training.position) {
                    Training(
                        id = it.id,
                        date = it.date,
                        muscleGroups = it.muscleGroups,
                        comment = it.comment,
                        weight = it.weight,
                        position = it.position - 1
                    )
                } else {
                    Training(
                        id = it.id,
                        date = it.date,
                        muscleGroups = it.muscleGroups,
                        comment = it.comment,
                        weight = it.weight,
                        position = it.position
                    )
                }
            })
        }
    }

    suspend fun forgotIdTraining() {
        withContext(Dispatchers.IO) {
            appSettings.setIdTraining(-1)
        }
    }

    private suspend fun reSaveTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.insertTraining(
                Training(
                    id = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position
                )
            )
        }
    }

}