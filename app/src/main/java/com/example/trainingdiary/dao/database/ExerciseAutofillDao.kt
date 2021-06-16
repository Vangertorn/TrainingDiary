package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.ExerciseAutofill
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseAutofillDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExerciseAutofill(exerciseAutofill: ExerciseAutofill): Long

    @Delete
    abstract fun deleteExerciseAutofill(exerciseAutofill: ExerciseAutofill)

    @Update
    abstract fun updateExerciseAutofill(exerciseAutofill: ExerciseAutofill)

    @Query("SELECT nameExercise FROM table_exercise_autofill ")
    abstract fun getExerciseAutofillStringFlow(): Flow<List<String>?>

    @Query("SELECT * FROM table_exercise_autofill ")
    abstract fun getExerciseAutofillFlow(): Flow<List<ExerciseAutofill>?>
}