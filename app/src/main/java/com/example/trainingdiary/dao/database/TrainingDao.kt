package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.models.info.TrainingInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(training: Training): Long

    @Delete
    abstract fun deleteTraining(training: Training)

    @Query("SELECT * FROM table_trainings")
    abstract fun getTrainingFlow(): Flow<List<Training>?>

    @Query("DELETE FROM table_trainings")
    abstract fun clearTrainingTable()

    @Insert
    abstract fun insertTrainings(trainings: List<Training>)

    @Transaction
    open fun updateTrainingTable(trainings: List<Training>) {
        clearTrainingTable()
        insertTrainings(trainings)

    }
    @Transaction
    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTrainingInfoFlow(id: Long): Flow<TrainingInfo?>

    @Transaction
    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTrainingInfo(id: Long): TrainingInfo?

}