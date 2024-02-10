package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_muscle_group",
    indices = [
        Index(
            value = ["nameMuscleGroup"],
            unique = true
        )
    ]
)
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val nameMuscleGroup: String,
    val deleted: Boolean,
    val isDefault: Boolean,
)
