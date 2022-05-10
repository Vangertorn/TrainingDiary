package com.yankin.trainingdiary.repository

import com.yankin.storage.TrainingStorage
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TrainingRepository(
    private val trainingStorage: TrainingStorage,
    private val appSettings: AppSettings
) {

    val currentTrainingAscFlow: Flow<List<Training>> =
        trainingStorage.getTrainingDeletedFalseAscFlow(false).map {
            it?.map {
                it.toModel()
            } ?: listOf()
        }
    val currentTrainingDescFlow: Flow<List<Training>> =
        trainingStorage.getTrainingDeletedFalseDescFlow(false).map {
            it?.map {
                it.toModel()
            } ?: listOf()
        }

    suspend fun saveTraining(training: Training) {

        withContext(Dispatchers.IO) {
            if (training.id == 0L) {
                val listPositions: MutableList<Int> =
                    trainingStorage.getTrainingPositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                trainingStorage.insertTraining(
                    Training(
                        date = training.date,
                        muscleGroups = training.muscleGroups,
                        comment = training.comment,
                        weight = training.weight,
                        position = listPositions[0] - 1,
                        selectedMuscleGroup = training.selectedMuscleGroup
                    ).toDomain()
                )
            }
        }
    }

    suspend fun updateTraining(training: Training) {
        withContext(Dispatchers.IO) {
            trainingStorage.updateTraining(training.toDomain())
        }
    }

    suspend fun deletedTrainingTrue(training: Training) {
        withContext(Dispatchers.IO) {
            trainingStorage.deletedTrainingFlags(
                Training(
                    id = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position,
                    deleted = true
                ).toDomain()
            )
        }
    }

    suspend fun deletedTrainingFalse(training: Training) {
        withContext(Dispatchers.IO) {
            trainingStorage.deletedTrainingFlags(
                Training(
                    id = training.id,
                    date = training.date,
                    muscleGroups = training.muscleGroups,
                    comment = training.comment,
                    weight = training.weight,
                    position = training.position,
                    deleted = false
                ).toDomain()
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
            return@withContext trainingStorage.getTraining(trainingId).toModel()
        }
    }

    suspend fun deletedTrainingsByFlags() {
        withContext(Dispatchers.IO) {
            trainingStorage.deletedTrainingsByFlags(true)
        }
    }
}
