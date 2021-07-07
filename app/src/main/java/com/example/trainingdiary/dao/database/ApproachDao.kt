package com.example.trainingdiary.dao.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.trainingdiary.models.Approach

@Dao
abstract class ApproachDao {

    @Insert
    abstract fun insertApproach(approach: Approach): Long

    @Delete
    abstract fun deleteApproach(approach: Approach)

}