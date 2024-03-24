package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "table_trainings", indices = [Index(value = ["id"], unique = true)])
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    val date: Long,
    val createTime: Long,
    val comment: String?,
    val personWeight: Double?,
    val inDeleteQueue: Boolean,
    val selectedMuscleGroup: List<Long>,
)
