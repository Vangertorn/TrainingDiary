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
            entity = TrainingEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idTraining"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ), ForeignKey(
            entity = SuperSetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idSet"),
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
    @ColumnInfo(index = true, name = "idTraining")
    val idTraining: Long? = null,
    val position: Int = 0,
    val comment: String? = null,
    val deleted: Boolean = false,
    @ColumnInfo(index = true, name = "idSet")
    val idSet: Long? = null
)
