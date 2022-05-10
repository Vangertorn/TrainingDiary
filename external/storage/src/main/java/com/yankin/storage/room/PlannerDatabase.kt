package com.yankin.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yankin.storage.room.entity.ApproachEntity
import com.yankin.storage.room.entity.ExerciseEntity
import com.yankin.storage.room.entity.ExerciseAutofillEntity
import com.yankin.storage.room.entity.MuscleGroupEntity
import com.yankin.storage.room.entity.SuperSetEntity
import com.yankin.storage.room.entity.TrainingEntity

const val DATABASE_NAME = "com.yankin.trainingdiary.dp"

@Database(
    entities = [
        TrainingEntity::class,
        ApproachEntity::class,
        ExerciseEntity::class,
        MuscleGroupEntity::class,
        ExerciseAutofillEntity::class,
        SuperSetEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDatabase : RoomDatabase() {

    abstract fun trainingDao(): com.yankin.storage.room.dao.TrainingDao
    abstract fun approachDao(): com.yankin.storage.room.dao.ApproachDao
    abstract fun exerciseDao(): com.yankin.storage.room.dao.ExerciseDao
    abstract fun muscleGroupDao(): com.yankin.storage.room.dao.MuscleGroupDao
    abstract fun exerciseAutofillDao(): com.yankin.storage.room.dao.ExerciseAutofillDao
    abstract fun superSetDao(): com.yankin.storage.room.dao.SuperSetDao
}
