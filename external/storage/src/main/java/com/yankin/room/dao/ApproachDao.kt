package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yankin.room.entity.ApproachEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ApproachDao {

    @Insert
    abstract fun insertApproach(approachEntity: ApproachEntity): Long

    @Delete
    abstract fun deleteApproach(approachEntity: ApproachEntity)

    @Query("SELECT * FROM table_approach WHERE idExercise == :exerciseId")
    abstract fun getApproachListStream(exerciseId: Long): Flow<List<ApproachEntity>?>

    @Query("SELECT * FROM table_approach WHERE idExercise == :exerciseId")
    abstract fun getApproachList(exerciseId: Long): List<ApproachEntity>?
}
