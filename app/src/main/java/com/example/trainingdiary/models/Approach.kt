package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "table_approach",
    foreignKeys = [ForeignKey(
        entity = Exercise::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idExercise"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Approach(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: String = "0",
    val reoccurrences: String = "0",
    @ColumnInfo(index = true, name = "idExercise")
    val idExercise: Long
) : Parcelable