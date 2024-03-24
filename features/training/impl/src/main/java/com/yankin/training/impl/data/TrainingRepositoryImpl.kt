package com.yankin.training.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.room.entity.TrainingEntity
import com.yankin.training.impl.domain.models.TrainingModel
import com.yankin.training.impl.domain.repositories.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class TrainingRepositoryImpl @Inject constructor(
    private val trainingLocalDataSource: TrainingLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : TrainingRepository {
    override val currentTrainingAscStream: Flow<List<TrainingModel>> =
        trainingLocalDataSource.getTrainingDeletedFalseAscFlow().map { trainingEntityList ->
            trainingEntityList?.map { trainingEntity -> trainingEntity.toModel() } ?: listOf()
        }
    override val currentTrainingDescStream: Flow<List<TrainingModel>> =
        trainingLocalDataSource.getTrainingDeletedFalseDescFlow().map { trainingEntityList ->
            trainingEntityList?.map { trainingEntity -> trainingEntity.toModel() } ?: listOf()
        }

    override suspend fun saveTraining(training: TrainingModel): Long = withContext(coroutineDispatchers.io) {
        return@withContext trainingLocalDataSource.insertTraining(
            TrainingEntity(
                date = training.date.value,
                comment = training.comment,
                personWeight = training.personWeight,
                selectedMuscleGroup = training.selectedMuscleGroup,
                inDeleteQueue = false,
                createTime = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun updateTraining(training: TrainingModel) {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.updateTraining(
                trainingId = training.id,
                trainingDate = training.date.value,
                comment = training.comment,
                personWeight = training.personWeight,
                trainingMuscleGroupIds = training.selectedMuscleGroup,
            )
        }
    }

    override suspend fun updateTrainingBlockDeleteQueue(trainingId: Long, addToDeleteQueue: Boolean) {
        withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.updateTrainingBlockDeleteQueue(
                trainingId = trainingId,
                addToDeleteQueue = addToDeleteQueue
            )
        }
    }

    override suspend fun clearDeleteQueue() {
        withContext(coroutineDispatchers.io){
            trainingLocalDataSource.clearDeleteQueue()
        }
    }

    override suspend fun getTrainingById(trainingId: Long): TrainingModel {
        return withContext(coroutineDispatchers.io) {
            trainingLocalDataSource.getTraining(trainingId).toModel()
        }
    }
}