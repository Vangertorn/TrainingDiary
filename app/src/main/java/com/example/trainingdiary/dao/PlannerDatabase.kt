package com.example.trainingdiary.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainingdiary.dao.database.ApproachDao
import com.example.trainingdiary.dao.database.ExerciseDao
import com.example.trainingdiary.dao.database.MuscleGroupDao
import com.example.trainingdiary.dao.database.TrainingDao
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.support.Converters

@Database(
    entities = [
        Training::class,
        Approach::class,
        Exercise::class,
        MuscleGroup::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao
    abstract fun approachDao(): ApproachDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun muscleGroupDao(): MuscleGroupDao
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