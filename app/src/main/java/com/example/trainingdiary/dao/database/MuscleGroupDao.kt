package com.example.trainingdiary.dao.database

import androidx.room.*
import com.example.trainingdiary.models.MuscleGroup
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMuscleGroup(muscleGroup: MuscleGroup): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMuscleGroups(muscleGroups: List<MuscleGroup>): List<Long>

    @Delete
    abstract fun deleteMuscleGroup(muscleGroup: MuscleGroup)

    @Query("SELECT * FROM table_muscle_group")
    abstract fun getMuscleGroupsFlow(): Flow<List<MuscleGroup>?>

    @Query("SELECT * FROM table_muscle_group")
    abstract fun getMuscleGroups(): List<MuscleGroup>?
}