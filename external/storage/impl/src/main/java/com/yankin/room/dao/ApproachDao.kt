package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.yankin.room.entity.ApproachEntity

@Dao
abstract class ApproachDao {

    @Insert
    abstract fun insertApproach(approachEntity: ApproachEntity): Long

    @Delete
    abstract fun deleteApproach(approachEntity: ApproachEntity)
}
