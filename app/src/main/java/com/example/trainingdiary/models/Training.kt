package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_trainings")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
//    val position: Int,
    val date: String,
    val muscleGroups: String? = null,
    val comment: String? = null,
    val weight: String? = null
) : Parcelable