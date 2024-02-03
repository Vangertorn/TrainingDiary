package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_membership",
    indices = [Index(value = ["id"], unique = true)]
)
data class MembershipEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    val trainingCount: Int?,
    val trainingCountPast: Int?,
    val startDate: Long,
    val endDate: Long?,
    val accountedTrainingIdList: List<Long>,
)