package com.yankin.di

import android.content.Context
import androidx.room.Room
import com.yankin.room.DATABASE_NAME
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.ExerciseNameDao
import com.yankin.room.dao.MuscleGroupDao
import com.yankin.room.dao.TrainingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideTrainingDao(plannerDatabase: PlannerDatabase): TrainingDao =
        plannerDatabase.trainingDao()

    @Provides
    @Singleton
    fun provideExerciseAutofillDao(plannerDatabase: PlannerDatabase): ExerciseNameDao =
        plannerDatabase.exerciseNameDao()

    @Provides
    @Singleton
    fun provideSuperSetDao(plannerDatabase: PlannerDatabase): MuscleGroupDao =
        plannerDatabase.muscleGroupDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlannerDatabase {
        return Room.databaseBuilder(context, PlannerDatabase::class.java, DATABASE_NAME).build()
    }
}