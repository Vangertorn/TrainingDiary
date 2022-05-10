package com.yankin.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "table_trainings", indices = [Index(value = ["id"], unique = true)])
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    val date: String,
    val muscleGroups: String? = null,
    val comment: String? = null,
    val weight: String? = null,
    val position: Int = 0,
    val deleted: Boolean = false,
    val selectedMuscleGroup: MutableList<Int> = mutableListOf()
)
