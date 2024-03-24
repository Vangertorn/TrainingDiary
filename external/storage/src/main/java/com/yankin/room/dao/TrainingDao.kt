package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yankin.room.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(trainingEntity: TrainingEntity): Long

    @Query("UPDATE table_trainings SET date = :trainingDate WHERE id == :trainingId")
    abstract fun updateTrainingDate(trainingId: Long, trainingDate: Long)

    @Query("UPDATE table_trainings SET comment = :comment WHERE id == :trainingId")
    abstract fun updateTrainingComment(trainingId: Long, comment: String?)

    @Query("UPDATE table_trainings SET personWeight = :personWeight WHERE id == :trainingId")
    abstract fun updateTrainingPersonWeight(trainingId: Long, personWeight: Double?)

    @Query("UPDATE table_trainings SET selectedMuscleGroup = :trainingMuscleGroupIds WHERE id == :trainingId")
    abstract fun updateTrainingMuscleGroupIds(trainingId: Long, trainingMuscleGroupIds: List<Long>)

    @Query("SELECT * FROM table_trainings WHERE inDeleteQueue == 0 ORDER BY createTime ASC")
    abstract fun getTrainingDeletedFalseAscFlow(): Flow<List<TrainingEntity>?>

    @Query("SELECT * FROM table_trainings WHERE inDeleteQueue == 0 ORDER BY createTime DESC")
    abstract fun getTrainingDeletedFalseDescFlow(): Flow<List<TrainingEntity>?>

    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTraining(id: Long): TrainingEntity

    @Query("UPDATE table_trainings SET inDeleteQueue = 1 WHERE id = :trainingId")
    abstract fun addToDeleteQueue(trainingId: Long)

    @Query("UPDATE table_trainings SET inDeleteQueue = 0 WHERE id = :trainingId")
    abstract fun removeFromDeleteQueue(trainingId: Long)

    @Query("DELETE FROM table_trainings WHERE inDeleteQueue == 1")
    abstract fun clearDeleteQueue()

    @Transaction
    open fun updateTraining(
        trainingId: Long,
        trainingDate: Long,
        comment: String?,
        personWeight: Double?,
        trainingMuscleGroupIds: List<Long>
    ) {
        updateTrainingDate(trainingId = trainingId, trainingDate = trainingDate)
        updateTrainingComment(trainingId = trainingId, comment = comment)
        updateTrainingPersonWeight(trainingId = trainingId, personWeight = personWeight)
        updateTrainingMuscleGroupIds(trainingId = trainingId, trainingMuscleGroupIds = trainingMuscleGroupIds)
    }
}
