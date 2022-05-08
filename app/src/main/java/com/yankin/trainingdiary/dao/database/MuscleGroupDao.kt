package com.yankin.trainingdiary.dao.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.trainingdiary.models.MuscleGroup
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroup(muscleGroup: MuscleGroup): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveMuscleGroups(muscleGroups: List<MuscleGroup>): List<Long>

    @Query("DELETE FROM table_muscle_group")
    abstract fun clearMuscleGroupTable()

    @Query("SELECT * FROM table_muscle_group WHERE deleted ==:flags ORDER BY id ASC ")
    abstract fun getMuscleGroupsByFlagsFlow(flags: Boolean): Flow<List<MuscleGroup>?>

    @Query("SELECT * FROM table_muscle_group")
    abstract fun getMuscleGroups(): List<MuscleGroup>?

    @Update
    abstract fun deletedMuscleGroupFlags(muscleGroup: MuscleGroup)

    @Transaction
    open fun recoverDefaultValues(muscleGroups: List<MuscleGroup>){
        clearMuscleGroupTable()
        saveMuscleGroups(muscleGroups)
    }
}