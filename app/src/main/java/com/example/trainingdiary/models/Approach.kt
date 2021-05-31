package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "table_approach")
data class Approach(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: String = "0",
    val reoccurrences: String = "0"
) : Parcelable