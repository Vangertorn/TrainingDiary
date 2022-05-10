package com.yankin.storage.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.yankin.storage.room.entity.ApproachEntity

@Dao
abstract class ApproachDao {

    @Insert
    abstract fun insertApproach(approachEntity: ApproachEntity): Long

    @Delete
    abstract fun deleteApproach(approachEntity: ApproachEntity)
}
