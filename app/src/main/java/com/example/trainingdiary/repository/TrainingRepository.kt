package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.TrainingDao
import com.example.trainingdiary.models.Training
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TrainingRepository(private val trainingDao: TrainingDao) {

    val currentTrainingFlow: Flow<List<Training>> = trainingDao.getTrainingFlow().map {
        it ?: listOf()
    }


    suspend fun saveTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.insertTraining(training)
        }
    }
    suspend fun deleteTraining(training: Training){
        withContext(Dispatchers.IO){
            trainingDao.deleteTraining(training)
        }
    }

}