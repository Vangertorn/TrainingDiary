package com.yankin.trainingdiary.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "table_super_set", indices = [Index(value = ["id"], unique = true)],
)
data class SuperSet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val idTraining: Long,
    val deleted: Boolean = false,
    val visibility: Boolean = false,
    val position: Int = 0
) : Parcelable