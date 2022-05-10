package com.yankin.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_super_set", indices = [Index(value = ["id"], unique = true)],
)
data class SuperSetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val idTraining: Long,
    val deleted: Boolean = false,
    val visibility: Boolean = false,
    val position: Int = 0
)
