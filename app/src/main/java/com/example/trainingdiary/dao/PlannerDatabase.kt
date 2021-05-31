package com.example.trainingdiary.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.Training

@Database(
    entities = [
        Training::class,
        Approach::class,
    Exercise::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PlannerDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun approachDao(): ApproachDao
    abstract fun exerciseDao(): ExerciseDao
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