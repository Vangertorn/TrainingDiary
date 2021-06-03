package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "table_exercise", indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Training::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idTraining"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)

data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val name: String,
    @ColumnInfo(index = true, name = "idTraining")
    val idTraining: Long,
    val position: Int = 0
) : Parcelable