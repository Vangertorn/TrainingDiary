package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.room.entity.ExerciseEntity
import com.yankin.room.entity.info.ViewHolderTypesEntity
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
    open fun deletedExercisesByFlags(flags: Boolean) {
        deleteExercises(getExercisesByFlags(flags))
    }

    @Query("SELECT * FROM table_exercise WHERE name==:emptyName LIMIT 1")
    abstract fun getEmptyExercise(emptyName: String): ExerciseEntity

    @Transaction
    open fun deletedEmptyExercise(emptyName: String) {
        deleteExercise(getEmptyExercise(emptyName))
    }

    @Update
    abstract fun updateExercise(exerciseEntity: ExerciseEntity)

    @Update
    abstract fun deletedExerciseFlags(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseFromId(id: Long): ExerciseEntity

    @Delete
    abstract fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Transaction
    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseInfoFlow(id: Long): Flow<ViewHolderTypesEntity.ExerciseInfo?>

    @Query("SELECT * FROM table_exercise WHERE id == :id LIMIT 1")
    abstract fun getExerciseInfo(id: Long): ViewHolderTypesEntity.ExerciseInfo

    @Query("SELECT * FROM table_exercise WHERE idSet == :id ")
    abstract fun getListExerciseInfo(id: Long): List<ViewHolderTypesEntity.ExerciseInfo>

    @Query("SELECT position FROM table_exercise ORDER BY position ASC")
    abstract fun getExercisePositions(): MutableList<Int>?

    @Query("UPDATE table_exercise SET position =:pos WHERE id = :id")
    abstract fun updateExercisePosition(id: Long, pos: Int)

    @Transaction
    open fun switchExercisePositions(exerciseEntity1: ExerciseEntity, exerciseEntity2: ExerciseEntity) {
        val exercise1Pos = exerciseEntity1.position
        val exercise2Pos = exerciseEntity2.position
        updateExercisePosition(exerciseEntity1.id, exercise2Pos)
        updateExercisePosition(exerciseEntity2.id, exercise1Pos)
    }

    @Query("SELECT * FROM table_exercise WHERE  deleted ==:flags AND idSet == :idSuperSet ORDER BY position DESC")
    abstract fun getExercisesInfoByBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<MutableList<ExerciseEntity>>

    @Query("SELECT * FROM table_exercise WHERE  deleted ==:flags AND idSet == :idSuperSet ORDER BY position DESC")
    abstract fun getExercisesInfoBySuperSetIdAndFlagsFlow(
        idSuperSet: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesEntity.ExerciseInfo>>

    @Query("SELECT * FROM table_exercise WHERE idTraining == :id AND deleted == :flags AND idSet is null ORDER BY position DESC")
    abstract fun getExercisesInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean
    ): Flow<List<ViewHolderTypesEntity.ExerciseInfo>>
}
