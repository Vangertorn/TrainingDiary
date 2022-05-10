package com.yankin.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.storage.room.entity.MuscleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroup(muscleGroupEntity: MuscleGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroups(muscleGroupEntities: List<MuscleGroupEntity>): List<Long>

    @Query("DELETE FROM table_muscle_group")
    abstract fun clearMuscleGroupTable()

    @Query("SELECT * FROM table_muscle_group WHERE deleted ==:flags ORDER BY id ASC ")
    abstract fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroupEntity>?>

    @Query("SELECT * FROM table_muscle_group")
    abstract fun getMuscleGroups(): List<MuscleGroupEntity>?

    @Update
    abstract fun deletedMuscleGroupFlags(muscleGroupEntity: MuscleGroupEntity)

    @Transaction
    open fun recoverDefaultValues(muscleGroupEntities: List<MuscleGroupEntity>) {
        clearMuscleGroupTable()
        saveMuscleGroups(muscleGroupEntities)
    }
}
