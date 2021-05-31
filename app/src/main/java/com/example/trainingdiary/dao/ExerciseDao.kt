package com.example.trainingdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainingdiary.models.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Insert
    abstract fun saveExercise(exercise: Exercise): Long

    @Delete
    abstract fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM table_exercise")
    abstract fun getExerciseFlow(): Flow<List<Exercise>?>
}