package com.yankin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yankin.room.dao.ApproachDao
import com.yankin.room.dao.ExerciseDao
import com.yankin.room.dao.ExerciseNameDao
import com.yankin.room.dao.MembershipDao
import com.yankin.room.dao.MuscleGroupDao
import com.yankin.room.dao.SuperSetDao
import com.yankin.room.dao.TrainingDao
import com.yankin.room.entity.ApproachEntity
import com.yankin.room.entity.ExerciseEntity
import com.yankin.room.entity.ExerciseNameEntity
import com.yankin.room.entity.MembershipEntity
import com.yankin.room.entity.MuscleGroupEntity
import com.yankin.room.entity.SuperSetEntity
import com.yankin.room.entity.TrainingEntity

const val DATABASE_NAME = "com.yankin.trainingdiary.dp"

@Database(
    entities = [
        TrainingEntity::class,
        ApproachEntity::class,
        ExerciseEntity::class,
        MuscleGroupEntity::class,
        ExerciseNameEntity::class,
        SuperSetEntity::class,
        MembershipEntity::class
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
    abstract fun exerciseNameDao(): ExerciseNameDao
    abstract fun superSetDao(): SuperSetDao
    abstract fun membershipDao(): MembershipDao
}
