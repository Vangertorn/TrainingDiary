package com.yankin.di

import com.yankin.impl.ExerciseStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.ExerciseDao
import com.yankin.storage.ExerciseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object ExerciseStorageModule {

    @Provides
    @Singleton
    fun provideExerciseDao(plannerDatabase: PlannerDatabase): ExerciseDao =
        plannerDatabase.exerciseDao()

    @Provides
    @Singleton
    fun provideExerciseStorage(exerciseDao: ExerciseDao): ExerciseStorage =
        ExerciseStorageImpl(exerciseDao)
}