package com.yankin.training_block.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.training_block.impl.domain.TrainingBlockModel
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class TrainingBlockRepositoryImpl @Inject constructor(
    private val trainingBlockDataSource: TrainingBlockDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : TrainingBlockRepository {

    override fun getTrainingBlockByTrainingIdStream(trainingId: Long): Flow<List<TrainingBlockModel>> {
        return trainingBlockDataSource.getTrainingBlockByTrainingIdStream(trainingId).map { trainingBlockEntityList ->
            trainingBlockEntityList.map { trainingBlockEntity ->
                trainingBlockEntity.toDomain()
            }
        }
    }

    override fun getTrainingBlockByIdStream(trainingBlockId: Long): Flow<TrainingBlockModel> {
        return trainingBlockDataSource.getTrainingBlockByIdStream(trainingBlockId).map { trainingBlockEntity ->
            trainingBlockEntity.toDomain()
        }
    }

    override suspend fun saveTrainingBlock(trainingBlock: TrainingBlockModel): Long {
        return withContext(coroutineDispatchers.io) {
            trainingBlockDataSource.insertTrainingBlock(trainingBlock = trainingBlock.toEntity())
        }
    }

    override suspend fun clearDeleteQueue() = withContext(coroutineDispatchers.io) {
        trainingBlockDataSource.clearDeleteQueue()
    }

    override suspend fun updateTrainingBlockDeleteQueue(trainingBlockId: Long, addToDeleteQueue: Boolean) {
        withContext(coroutineDispatchers.io) {
            trainingBlockDataSource.updateTrainingBlockDeleteQueue(
                trainingBlockId = trainingBlockId,
                addToDeleteQueue = addToDeleteQueue
            )
        }
    }

    override suspend fun switchTrainingBlockPosition(
        firstTrainingBlockId: Long,
        secondTrainingBlockId: Long,
    ) {
        withContext(coroutineDispatchers.io) {
            trainingBlockDataSource.switchTrainingBlockPositions(
                firstTrainingBlockId = firstTrainingBlockId,
                secondTrainingBlockId = secondTrainingBlockId,
            )
        }
    }
}