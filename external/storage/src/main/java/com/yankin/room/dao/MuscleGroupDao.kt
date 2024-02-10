package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yankin.room.entity.MuscleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroup(muscleGroupEntity: MuscleGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroups(muscleGroupEntities: List<MuscleGroupEntity>): List<Long>

    @Query("DELETE FROM table_muscle_group WHERE isDefault == 0")
    abstract fun clearMuscleGroupTable()

    @Query("UPDATE table_muscle_group SET deleted = 0 WHERE isDefault== 1")
    abstract fun restoreMuscleGroupTable()

    @Query("SELECT * FROM table_muscle_group WHERE deleted ==:flags ORDER BY id ASC ")
    abstract fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroupEntity>?>

    @Query("SELECT * FROM table_muscle_group")
    abstract fun getMuscleGroups(): List<MuscleGroupEntity>?

    @Query("UPDATE table_muscle_group SET deleted = 1 WHERE id==:muscleGroupId ")
    abstract fun deletedMuscleGroup(muscleGroupId: Long)

    @Transaction
    open fun recoverDefaultValues() {
        clearMuscleGroupTable()
        restoreMuscleGroupTable()
    }
}
