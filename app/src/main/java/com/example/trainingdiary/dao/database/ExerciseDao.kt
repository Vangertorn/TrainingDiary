package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.info.ExerciseInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Insert
    abstract fun insertExercise(exercise: Exercise): Long

    @Delete
    abstract fun deleteExercises(exercises: List<Exercise>)

    @Query("SELECT * FROM table_exercise WHERE deleted ==:flags")
    abstract fun getExercisesByFlags(
        flags: Boolean
    ): List<Exercise>

    @Transaction
    open fun deletedExercisesByFlags(flags: Boolean) {
        deleteExercises(getExercisesByFlags(flags))
    }

    @Update
    abstract fun updateExercise(exercise: Exercise)

    @Update
    abstract fun deletedExerciseFlags(exercise: Exercise)

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFromId(id: Long): Exercise

    @Delete
    abstract fun deleteExercise(exercise: Exercise)

    @Transaction
    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseInfoFlow(id: Long): Flow<ExerciseInfo?>

    @Query("SELECT position FROM table_exercise ORDER BY position ASC")
    abstract fun getExercisePositions(): MutableList<Int>?

    @Query("UPDATE table_exercise SET position =:pos WHERE id = :id")
    abstract fun updateExercisePosition(id: Long, pos: Int)

    @Transaction
    open fun switchExercisePositions(exercise1: Exercise, exercise2: Exercise) {
        val exercise1Pos = exercise1.position
        val exercise2Pos = exercise2.position
        updateExercisePosition(exercise1.id, exercise2Pos)
        updateExercisePosition(exercise2.id, exercise1Pos)
    }

    @Query("SELECT * FROM table_exercise WHERE  deleted ==:flags AND idSet == :idSuperSet ORDER BY position DESC")
    abstract fun getExercisesInfoByBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<MutableList<Exercise>>

}