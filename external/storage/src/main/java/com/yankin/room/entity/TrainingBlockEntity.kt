package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_training_block", indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = TrainingEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("trainingId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TrainingBlockEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(index = true, name = "trainingId")
    val trainingId: Long,
    val inDeleteQueue: Boolean,
    val position: Int,
)