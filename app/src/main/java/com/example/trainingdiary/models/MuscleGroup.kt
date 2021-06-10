package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_muscle_group",  indices = [Index(value = ["nameMuscleGroup"], unique = true)])
data class MuscleGroup(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val nameMuscleGroup: String,
    val factorySettings: Boolean
) : Parcelable