package com.example.trainingdiary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainingdiary.models.Approach
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ApproachDao {

    @Insert
    abstract fun insertApproach(approach: Approach): Long

    @Delete
    abstract fun deleteApproach(approach: Approach)

    @Query("SELECT*FROM table_approach")
    abstract fun getApproachFlow(): Flow<List<Approach>?>



}