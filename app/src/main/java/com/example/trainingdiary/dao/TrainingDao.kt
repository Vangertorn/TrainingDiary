package com.example.trainingdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainingdiary.models.Training
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(training: Training): Long

    @Delete
    abstract fun deleteTraining(training: Training)

    @Query("SELECT * FROM table_trainings")
    abstract fun getTrainingFlow(): Flow<List<Training>?>
}