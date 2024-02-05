package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yankin.room.entity.ExercisePatternEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExercisePatternDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(exercisePatternEntity: ExercisePatternEntity): Long

    @Delete
    abstract fun delete(exercisePatternEntity: ExercisePatternEntity)

    @Update
    abstract fun update(exercisePatternEntity: ExercisePatternEntity)

    @Query("SELECT name FROM table_exercise_pattern ")
    abstract fun getExercisePatternStringFlow(): Flow<List<String>?>

    @Query("SELECT * FROM table_exercise_pattern ")
    abstract fun getExercisePatternFlow(): Flow<List<ExercisePatternEntity>?>
}
