package com.example.trainingdiary.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainingdiary.dao.database.*
import com.example.trainingdiary.models.*
import com.example.trainingdiary.support.Converters

@Database(
    entities = [
        Training::class,
        Approach::class,
        Exercise::class,
        MuscleGroup::class,
        ExerciseAutofill::class,
        SuperSet::class
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
    abstract fun exerciseAutofillDao(): ExerciseAutofillDao
    abstract fun superSetDao():SuperSetDao
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