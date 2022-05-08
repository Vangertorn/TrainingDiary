package com.yankin.trainingdiary.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yankin.trainingdiary.dao.database.ApproachDao
import com.yankin.trainingdiary.dao.database.ExerciseAutofillDao
import com.yankin.trainingdiary.dao.database.ExerciseDao
import com.yankin.trainingdiary.dao.database.MuscleGroupDao
import com.yankin.trainingdiary.dao.database.SuperSetDao
import com.yankin.trainingdiary.dao.database.TrainingDao
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.ExerciseAutofill
import com.yankin.trainingdiary.models.MuscleGroup
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.support.Converters

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
    abstract fun superSetDao(): SuperSetDao

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