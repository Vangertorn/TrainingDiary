package com.yankin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yankin.room.dao.SetDao
import com.yankin.room.dao.ExerciseDao
import com.yankin.room.dao.ExercisePatternDao
import com.yankin.room.dao.MembershipDao
import com.yankin.room.dao.MuscleGroupDao
import com.yankin.room.dao.SuperSetDao
import com.yankin.room.dao.TrainingDao
import com.yankin.room.entity.SetEntity
import com.yankin.room.entity.ExerciseEntity
import com.yankin.room.entity.ExercisePatternEntity
import com.yankin.room.entity.MembershipEntity
import com.yankin.room.entity.MuscleGroupEntity
import com.yankin.room.entity.SuperSetEntity
import com.yankin.room.entity.TrainingEntity

const val DATABASE_NAME = "com.yankin.trainingdiary.dp"

@Database(
    entities = [
        TrainingEntity::class,
        SetEntity::class,
        ExerciseEntity::class,
        MuscleGroupEntity::class,
        ExercisePatternEntity::class,
        SuperSetEntity::class,
        MembershipEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao
    abstract fun setDao(): SetDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun muscleGroupDao(): MuscleGroupDao
    abstract fun exercisePatternDao(): ExercisePatternDao
    abstract fun superSetDao(): SuperSetDao
    abstract fun membershipDao(): MembershipDao
}
