package com.yankin.trainingdiary.dao.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {

    @Insert
    abstract fun insertTraining(training: Training): Long

    @Delete
    abstract fun deleteTrainings(trainings: List<Training>)

    @Update
    abstract fun updateTraining(training: Training)

    @Update
    abstract fun deletedTrainingFlags(training: Training)

    @Query("SELECT * FROM table_trainings WHERE deleted == :flags ORDER BY position ASC")
    abstract fun getTrainingDeletedFalseAscFlow(flags: Boolean): Flow<List<Training>?>

    @Query("SELECT * FROM table_trainings WHERE deleted == :flags ORDER BY position DESC")
    abstract fun getTrainingDeletedFalseDescFlow(flags: Boolean): Flow<List<Training>?>

    @Query("DELETE FROM table_trainings")
    abstract fun clearTrainingTable()

    @Insert
    abstract fun insertTrainings(trainings: List<Training>)

    @Transaction
    open fun updateTrainingTable(trainings: List<Training>) {
        clearTrainingTable()
        insertTrainings(trainings)
    }

    @Query("SELECT * FROM table_exercise WHERE idTraining == :id AND deleted ==:flags AND idSet is null ORDER BY position DESC")
    abstract fun getExercisesInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypes.ExerciseInfo>>

    @Query("SELECT * FROM table_trainings WHERE deleted ==:flags")
    abstract fun getTrainingByFlags(
        flags: Boolean
    ): List<Training>

    @Transaction
    open fun deletedTrainingsByFlags(flags: Boolean) {
        val list = getTrainingByFlags(flags)
        deleteTrainings(list)
    }

    @Query("SELECT * FROM table_exercise WHERE idTraining == :id")
    abstract fun getExercisesInfoByTrainingId(id: Long): List<ViewHolderTypes.ExerciseInfo>

    @Query("SELECT * FROM table_trainings WHERE id == :id LIMIT 1")
    abstract fun getTraining(id: Long): Training

    @Query("SELECT position FROM table_trainings ORDER BY position ASC")
    abstract fun getTrainingPositions(): MutableList<Int>?
}