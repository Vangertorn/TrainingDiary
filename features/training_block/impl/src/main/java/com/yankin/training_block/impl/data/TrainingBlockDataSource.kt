package com.yankin.training_block.impl.data

import com.yankin.room.dao.TrainingBlockDao
import com.yankin.room.entity.TrainingBlockEntity
import com.yankin.training_block.api.NotValidTrainingBlockDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingBlockDataSource @Inject constructor(
    private val db: TrainingBlockDao
) {
    fun getTrainingBlockByTrainingIdStream(id: Long): Flow<List<TrainingBlockEntity>> {
        return db.getTrainingBlockByTrainingIdStream(trainingId = id).map { trainingBlockEntityList ->
            trainingBlockEntityList ?: emptyList()
        }
    }

    fun getTrainingBlockByIdStream(trainingBlockId: Long): Flow<TrainingBlockEntity> {
        return db.getTrainingBlockByIdStream(trainingBlockId = trainingBlockId).map { trainingBlockEntity ->
            trainingBlockEntity ?: throw NotValidTrainingBlockDomain()
        }
    }

    fun updateTrainingBlockDeleteQueue(trainingBlockId: Long, addToDeleteQueue: Boolean) {
        if (addToDeleteQueue) {
            db.addToDeleteQueue(trainingBlockId)
        } else {
            db.removeFromDeleteQueue(trainingBlockId)
        }
    }

    fun insertTrainingBlock(trainingBlock: TrainingBlockEntity): Long {
        val positionList = db.getTrainingBlockPositions(trainingBlock.trainingId)
        return db.insertTrainingBlock(
            trainingBlock = trainingBlock.copy(
                position = if (positionList.isEmpty()) 0 else positionList.last() + 1
            )
        )
    }

    fun clearDeleteQueue() {
      db.clearDeleteQueue()
    }

    fun switchTrainingBlockPositions(
        firstTrainingBlockId: Long,
        secondTrainingBlockId: Long,
    ) {
        db.switchTrainingBlockPositions(
            firstTrainingBlockId = firstTrainingBlockId,
            secondTrainingBlockId = secondTrainingBlockId,
        )
    }
}