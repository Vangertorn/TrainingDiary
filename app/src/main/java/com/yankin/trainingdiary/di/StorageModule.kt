package com.yankin.trainingdiary.di

import android.content.Context
import androidx.room.Room
import com.yankin.trainingdiary.dao.DATABASE_NAME
import com.yankin.trainingdiary.dao.PlannerDatabase
import com.yankin.trainingdiary.dao.database.ApproachDao
import com.yankin.trainingdiary.dao.database.ExerciseAutofillDao
import com.yankin.trainingdiary.dao.database.ExerciseDao
import com.yankin.trainingdiary.dao.database.MuscleGroupDao
import com.yankin.trainingdiary.dao.database.SuperSetDao
import com.yankin.trainingdiary.dao.database.TrainingDao
import com.yankin.trainingdiary.datastore.AppSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {

    @Provides
    @Singleton
    fun provideAppSetting(@ApplicationContext context: Context): AppSettings {
        return AppSettings(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlannerDatabase {
        return Room.databaseBuilder(context, PlannerDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideApproachDao(plannerDatabase: PlannerDatabase): ApproachDao =
        plannerDatabase.approachDao()

    @Provides
    @Singleton
    fun provideExerciseDao(plannerDatabase: PlannerDatabase): ExerciseDao =
        plannerDatabase.exerciseDao()

    @Provides
    @Singleton
    fun provideExerciseAutofillDao(plannerDatabase: PlannerDatabase): ExerciseAutofillDao =
        plannerDatabase.exerciseAutofillDao()

    @Provides
    @Singleton
    fun provideTrainingDao(plannerDatabase: PlannerDatabase): TrainingDao =
        plannerDatabase.trainingDao()

    @Provides
    @Singleton
    fun provideMuscleGroupDao(plannerDatabase: PlannerDatabase): MuscleGroupDao =
        plannerDatabase.muscleGroupDao()

    @Provides
    @Singleton
    fun provideSuperSetDao(plannerDatabase: PlannerDatabase): SuperSetDao =
        plannerDatabase.superSetDao()
}