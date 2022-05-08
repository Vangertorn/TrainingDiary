package com.yankin.trainingdiary.repository

import com.yankin.trainingdiary.dao.database.TrainingDao
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Training
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TrainingRepository(
    private val trainingDao: TrainingDao,
    private val appSettings: AppSettings,
) {

    val currentTrainingAscFlow: Flow<List<Training>> =
        trainingDao.getTrainingDeletedFalseAscFlow(false).map {
            it ?: listOf()
        }
    val currentTrainingDescFlow: Flow<List<Training>> =
        trainingDao.getTrainingDeletedFalseDescFlow(false).map {
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
                        position = listPositions[0] - 1,
                        selectedMuscleGroup = training.selectedMuscleGroup
                    )
                )
            }
        }
    }

    suspend fun updateTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingDao.updateTraining(training)
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

    suspend fun getTrainingById(trainingId: Long): Training {
        return withContext(Dispatchers.IO) {
            return@withContext trainingDao.getTraining(trainingId)
        }
    }

    suspend fun deletedTrainingsByFlags() {
        withContext(Dispatchers.IO) {
            trainingDao.deletedTrainingsByFlags(true)
        }
    }
}