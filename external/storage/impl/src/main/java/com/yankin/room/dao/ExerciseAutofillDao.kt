package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yankin.room.entity.ExerciseAutofillEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseAutofillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExerciseAutofill(exerciseAutofillEntity: ExerciseAutofillEntity): Long

    @Delete
    abstract fun deleteExerciseAutofill(exerciseAutofillEntity: ExerciseAutofillEntity)

    @Update
    abstract fun updateExerciseAutofill(exerciseAutofillEntity: ExerciseAutofillEntity)

    @Query("SELECT nameExercise FROM table_exercise_autofill ")
    abstract fun getExerciseAutofillStringFlow(): Flow<List<String>?>

    @Query("SELECT * FROM table_exercise_autofill ")
    abstract fun getExerciseAutofillFlow(): Flow<List<ExerciseAutofillEntity>?>
}
