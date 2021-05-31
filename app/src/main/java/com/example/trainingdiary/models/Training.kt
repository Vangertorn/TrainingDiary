package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.FieldPosition

@Parcelize
@Entity(tableName = "table_trainings", indices = [Index(value = ["id"], unique = true)])
data class Training(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    val date: String,
    val muscleGroups: String? = null,
    val comment: String? = null,
    val weight: String? = null,
    val position: Int = 0
) : Parcelable