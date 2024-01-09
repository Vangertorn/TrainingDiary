package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yankin.room.entity.ExerciseNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExerciseAutofill(exerciseNameEntity: ExerciseNameEntity): Long

    @Delete
    abstract fun deleteExerciseAutofill(exerciseNameEntity: ExerciseNameEntity)

    @Update
    abstract fun updateExerciseAutofill(exerciseNameEntity: ExerciseNameEntity)

    @Query("SELECT nameExercise FROM table_exercise_name ")
    abstract fun getExerciseAutofillStringFlow(): Flow<List<String>?>

    @Query("SELECT * FROM table_exercise_name ")
    abstract fun getExerciseAutofillFlow(): Flow<List<ExerciseNameEntity>?>
}
