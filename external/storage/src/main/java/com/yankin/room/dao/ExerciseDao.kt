package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yankin.room.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Insert
    abstract fun insertExercise(exerciseEntity: ExerciseEntity): Long

    @Delete
    abstract fun deleteExercises(exerciseEntities: List<ExerciseEntity>)

    @Query("SELECT * FROM table_exercise WHERE deleted ==:flags")
    abstract fun getExercisesByFlags(
        flags: Boolean
    ): List<ExerciseEntity>

    @Transaction
    open fun deletedExercisesByFlag(flags: Boolean) {
        deleteExercises(getExercisesByFlags(flags))
    }

    @Query("DELETE FROM table_exercise WHERE name=='' ")
    abstract fun deletedEmptyExercise()

    @Query("UPDATE table_exercise SET name = :exerciseName, comment = :exerciseComment WHERE id= :exerciseId ")
    abstract fun updateExercise(exerciseId: Long, exerciseName: String, exerciseComment: String?)

    @Query("UPDATE table_exercise SET deleted = :deleteFlag WHERE id = :exerciseId")
    abstract fun updateExerciseDeleteFlagById(exerciseId: Long, deleteFlag: Boolean)

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFromIdStream(id: Long): Flow<ExerciseEntity>

    @Delete
    abstract fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Query("DELETE FROM table_exercise WHERE id = :exerciseId")
    abstract fun deleteExerciseById(exerciseId: Long)

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFlow(id: Long): Flow<ExerciseEntity?>

    @Query("SELECT position FROM table_exercise ORDER BY position ASC")
    abstract fun getExercisePositions(): MutableList<Int>?

    @Query("UPDATE table_exercise SET position =:pos WHERE id = :id")
    abstract fun updateExercisePosition(id: Long, pos: Int)

    @Transaction
    open fun switchExercisePositions(
        firstExerciseId: Long,
        firstExercisePosition: Int,
        secondExerciseId: Long,
        secondExercisePosition: Int
    ) {
        updateExercisePosition(id = firstExerciseId, pos = firstExercisePosition)
        updateExercisePosition(id = secondExerciseId, pos = secondExercisePosition)
    }

    @Query("SELECT * FROM table_exercise WHERE  deleted ==:flags AND idSet == :superSetId ORDER BY position DESC")
    abstract fun getExercisesInfoBySuperSetId(
        superSetId: Long,
        flags: Boolean
    ): List<ExerciseEntity>

    @Query("SELECT * FROM table_exercise WHERE idTraining == :trainingId AND deleted == :flags AND idSet is null ORDER BY position DESC")
    abstract fun getExercisesByTrainingIdFlow(trainingId: Long, flags: Boolean): Flow<List<ExerciseEntity>?>

    @Query("SELECT * FROM table_exercise WHERE  deleted ==:flags AND idSet == :superSetId ORDER BY position DESC")
    abstract fun getExercisesInfoBySuperSetIdFlow(
        superSetId: Long,
        flags: Boolean
    ): Flow<List<ExerciseEntity>>
}
