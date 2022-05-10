package com.yankin.storage.di

import com.yankin.storage.ExerciseStorage
import com.yankin.storage.impl.ExerciseStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.ExerciseDao
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
    fun provideExerciseDao(plannerDatabase: PlannerDatabase): com.yankin.storage.room.dao.ExerciseDao =
        plannerDatabase.exerciseDao()

    @Provides
    @Singleton
    fun provideExerciseStorage(exerciseDao: ExerciseDao): ExerciseStorage =
        ExerciseStorageImpl(exerciseDao)
}