package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Insert
    abstract fun insertExercise(exerciseEntity: ExerciseEntity): Long

    @Query("UPDATE table_exercise SET name = :exerciseName, comment = :exerciseComment WHERE id= :exerciseId ")
    abstract fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?)

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFromIdStream(id: Long): Flow<ExerciseEntity>

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFlow(id: Long): Flow<ExerciseEntity?>

    @Query("SELECT * FROM table_exercise WHERE trainingBlockId == :trainingBlockId ORDER BY position DESC")
    abstract fun getExercisesByTrainingBlockIdStream(trainingBlockId: Long): Flow<List<ExerciseEntity>?>

    @Query("SELECT * FROM table_exercise WHERE  trainingBlockId == :superSetId ORDER BY position DESC")
    abstract fun getExercisesInfoBySuperSetIdFlow(
        superSetId: Long,
    ): Flow<List<ExerciseEntity>>
}
