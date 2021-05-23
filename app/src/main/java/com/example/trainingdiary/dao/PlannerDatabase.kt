package com.example.trainingdiary.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trainingdiary.models.Training

@Database(
    entities = [
        Training::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PlannerDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}

object DatabaseConstructor {
    fun create(context: Context): PlannerDatabase {
        return Room.databaseBuilder(
            context,
            PlannerDatabase::class.java,
            "com.example.myapplication.dp"
        ).build()
    }
}