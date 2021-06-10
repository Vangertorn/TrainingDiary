package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.models.info.ExerciseInfo
import com.example.trainingdiary.models.info.TrainingInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(training: Training): Long

    @Delete
    abstract fun deleteTraining(training: Training)


    @Update
    abstract fun deletedTrainingFlags(training: Training)

    @Query("SELECT * FROM table_trainings")
    abstract fun getTrainingFlow(): Flow<List<Training>?>


    @Query("SELECT * FROM table_trainings WHERE deleted == :flags")
    abstract fun getTrainingDeletedFalseFlow(flags: Boolean): Flow<List<Training>?>

    @Query("SELECT * FROM table_trainings")
    abstract fun getTrainings(): List<Training>?

    @Query("DELETE FROM table_trainings")
    abstract fun clearTrainingTable()

    @Insert
    abstract fun insertTrainings(trainings: List<Training>)

    @Transaction
    open fun updateTrainingTable(trainings: List<Training>) {
        clearTrainingTable()
        insertTrainings(trainings)

    }

    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTrainingInfoFlow(id: Long): Flow<TrainingInfo?>

    @Query("SELECT * FROM table_exercise WHERE idTraining == :id AND deleted ==:flags")
    abstract fun getExercisesInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean
    ): Flow<List<ExerciseInfo>>

    @Query("SELECT * FROM table_exercise WHERE idTraining == :id")
    abstract fun getExercisesInfoByTrainingId(id: Long): List<ExerciseInfo>


    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTrainingInfo(id: Long): TrainingInfo?

    @Query("UPDATE table_trainings SET position =:pos WHERE id = :id")
    abstract fun updateTrainingPosition(id: Long, pos: Int)

    @Transaction
    open fun switchTrainingPositions(training1: Training, training2: Training) {
        val training1Pos = training1.position
        val training2Pos = training2.position
        updateTrainingPosition(training1.id, training2Pos)
        updateTrainingPosition(training2.id, training1Pos)
    }

    @Query("SELECT position FROM table_trainings ORDER BY position ASC")
    abstract fun getTrainingPositions(): MutableList<Int>?

}