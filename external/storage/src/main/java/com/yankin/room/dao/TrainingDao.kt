package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.room.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(trainingEntity: TrainingEntity): Long

    @Delete
    abstract fun deleteTrainings(trainingEntities: List<TrainingEntity>)

    @Update
    abstract fun updateTraining(trainingEntity: TrainingEntity)

    @Update
    abstract fun deletedTrainingFlags(trainingEntity: TrainingEntity)

    @Query("SELECT * FROM table_trainings WHERE deleted == :flags ORDER BY position ASC")
    abstract fun getTrainingDeletedFalseAscFlow(flags: Boolean): Flow<List<TrainingEntity>?>

    @Query("SELECT * FROM table_trainings WHERE deleted == :flags ORDER BY position DESC")
    abstract fun getTrainingDeletedFalseDescFlow(flags: Boolean): Flow<List<TrainingEntity>?>

    @Query("DELETE FROM table_trainings")
    abstract fun clearTrainingTable()

    @Insert
    abstract fun insertTrainings(trainingEntities: List<TrainingEntity>)

    @Transaction
    open fun updateTrainingTable(trainingEntities: List<TrainingEntity>) {
        clearTrainingTable()
        insertTrainings(trainingEntities)
    }

    @Query("SELECT * FROM table_trainings WHERE deleted ==:flags")
    abstract fun getTrainingByFlags(
        flags: Boolean
    ): List<TrainingEntity>

    @Transaction
    open fun deletedTrainingsByFlag(flags: Boolean) {
        val list = getTrainingByFlags(flags)
        deleteTrainings(list)
    }

    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTraining(id: Long): TrainingEntity

    @Query("SELECT position FROM table_trainings ORDER BY position ASC")
    abstract fun getTrainingPositions(): MutableList<Int>?
}
