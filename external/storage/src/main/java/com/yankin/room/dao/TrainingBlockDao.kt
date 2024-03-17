package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yankin.room.entity.TrainingBlockEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingBlockDao {

    @Insert
    abstract fun insertTrainingBlock(trainingBlock: TrainingBlockEntity): Long

    @Query("UPDATE table_training_block SET inDeleteQueue = 1 WHERE id = :trainingBlockId")
    abstract fun addToDeleteQueue(trainingBlockId: Long)

    @Query("UPDATE table_training_block SET inDeleteQueue = 0 WHERE id = :trainingBlockId")
    abstract fun removeFromDeleteQueue(trainingBlockId: Long)

    @Query("SELECT * FROM table_training_block WHERE trainingId == :trainingId AND inDeleteQueue == 0 ORDER BY position DESC")
    abstract fun getTrainingBlockByTrainingIdStream(trainingId: Long): Flow<List<TrainingBlockEntity>?>

    @Query("SELECT * FROM table_training_block WHERE id == :trainingBlockId AND inDeleteQueue == 0 ORDER BY position DESC")
    abstract fun getTrainingBlockByIdStream(trainingBlockId: Long): Flow<TrainingBlockEntity?>

    @Query("SELECT position FROM table_training_block WHERE trainingId == :trainingId AND inDeleteQueue == 0")
    abstract fun getTrainingBlockPositions(trainingId: Long): List<Int>

    @Query("DELETE FROM table_training_block WHERE inDeleteQueue == 1")
    abstract fun clearDeleteQueue()

    @Query("UPDATE table_training_block SET position =:pos WHERE id = :id")
    abstract fun updateTrainingBlockPosition(id: Long, pos: Int)

    @Query("SELECT position FROM table_training_block WHERE id =:id")
    abstract fun getTrainingBlockPosition(id: Long): Int

    @Transaction
    open fun switchTrainingBlockPositions(
        firstTrainingBlockId: Long,
        secondTrainingBlockId: Long,
    ) {
        val currentFirstPosition = getTrainingBlockPosition(firstTrainingBlockId)
        val currentSecondPosition = getTrainingBlockPosition(secondTrainingBlockId)
        updateTrainingBlockPosition(id = firstTrainingBlockId, pos = currentSecondPosition)
        updateTrainingBlockPosition(id = secondTrainingBlockId, pos = currentFirstPosition)
    }
}
