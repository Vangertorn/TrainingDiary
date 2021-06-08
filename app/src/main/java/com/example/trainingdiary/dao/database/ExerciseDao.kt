package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.info.ExerciseInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Insert
    abstract fun insertExercise(exercise: Exercise): Long

    @Insert
    abstract fun insertExercises(exercises: List<Exercise>): List<Long>

    @Delete
    abstract fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM table_exercise")
    abstract fun getExerciseFlow(): Flow<List<Exercise>?>

    @Transaction
    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseInfo(id: Long): ExerciseInfo?

    @Transaction
    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseInfoFlow(id: Long): Flow<ExerciseInfo?>

}