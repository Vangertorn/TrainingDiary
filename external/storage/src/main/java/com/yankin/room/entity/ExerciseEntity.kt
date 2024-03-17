package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_exercise", indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = TrainingBlockEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("trainingBlockId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)

data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val name: String,
    @ColumnInfo(index = true, name = "trainingBlockId")
    val trainingBlockId: Long,
    val position: Int,
    val comment: String?,
)
