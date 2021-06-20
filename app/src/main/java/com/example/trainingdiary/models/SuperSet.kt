package com.example.trainingdiary.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "table_super_set", indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Training::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idTraining"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class SuperSet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(index = true, name = "idTraining")
    val idTraining: Long,
    val deleted: Boolean = false,
    val visibility: Boolean = false,
    val position: Int = 0
) : Parcelable