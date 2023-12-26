package com.yankin.training.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.preferences.AppSettings
import com.yankin.room.entity.TrainingEntity
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.impl.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class TrainingRepositoryImpl @Inject constructor(
    private val trainingLocalDataSource: TrainingLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val appSettings: AppSettings,
) : TrainingRepository {
    override val currentTrainingAscStream: Flow<List<TrainingDomain>> =
        trainingLocalDataSource.getTrainingDeletedFalseAscFlow(false).map { trainingEntityList ->
            trainingEntityList?.map { trainingEntity -> trainingEntity.toDomain() } ?: listOf()
        }
    override val currentTrainingDescStream: Flow<List<TrainingDomain>> =
        trainingLocalDataSource.getTrainingDeletedFalseDescFlow(false).map { trainingEntityList ->
            trainingEntityList?.map { trainingEntity -> trainingEntity.toDomain() } ?: listOf()
        }

    override suspend fun saveTraining(training: TrainingDomain) {
        withContext(coroutineDispatchers.io) {
            if (training.id == 0L) {
                val listPositions: MutableList<Int> =
                    trainingLocalDataSource.getTrainingPositions() ?: arrayListOf(0)
                if (listPositions.isEmpty()) listPositions.add(500)
                trainingLocalDataSource.insertTraining(
                    TrainingEntity(
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

    override suspend fun updateTraining(training: TrainingDomain) {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.updateTraining(training.toEntity())
        }
    }

    override suspend fun deletedTrainingTrue(training: TrainingDomain) {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.deletedTrainingFlags(
                TrainingEntity(
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

    override suspend fun deletedTrainingFalse(training: TrainingDomain) {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.deletedTrainingFlags(
                TrainingEntity(
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

    override suspend fun getTrainingById(trainingId: Long): TrainingDomain {
        return withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.getTraining(trainingId).toDomain()
        }
    }

    override suspend fun deleteTrainingsByFlags() {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.deletedTrainingsByFlags(true)
        }
    }

    override suspend fun forgotIdTraining() {
        withContext(coroutineDispatchers.io) {
           appSettings.setIdTraining(-1)
        }
    }
}