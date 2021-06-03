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
            trainingDao.insertTraining(training)
        }
    }

    suspend fun deleteTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.deleteTraining(training)
        }
    }

    suspend fun forgotIdTraining() {
        withContext(Dispatchers.IO) {
            appSettings.setIdTraining(-1)
        }
    }

}