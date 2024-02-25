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

    @Query("DELETE FROM table_exercise_pattern WHERE id = :exercisePatternId")
    abstract fun delete(exercisePatternId: Long)

    @Query("UPDATE table_exercise_pattern SET name =:newName WHERE name = :oldName")
    abstract fun updateByName(oldName: String, newName: String)

    @Query("SELECT * FROM table_exercise_pattern WHERE id = :exercisePatternId")
    abstract fun getExercisePatternById(exercisePatternId: Long): ExercisePatternEntity

    @Update
    abstract fun update(exercisePatternEntity: ExercisePatternEntity)

    @Query("SELECT name FROM table_exercise_pattern ")
    abstract fun getExercisePatternStringFlow(): Flow<List<String>?>

    @Query("SELECT * FROM table_exercise_pattern ")
    abstract fun getExercisePatternFlow(): Flow<List<ExercisePatternEntity>?>
}
