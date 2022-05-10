package com.yankin.storage.di

import com.yankin.storage.TrainingStorage
import com.yankin.storage.impl.TrainingStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.TrainingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
  object TrainingStorageModule {

    @Provides
    @Singleton
    fun provideTrainingDao(plannerDatabase: PlannerDatabase): TrainingDao =
        plannerDatabase.trainingDao()

    @Provides
    @Singleton
    fun provideTrainingStorage(trainingDao: TrainingDao): TrainingStorage =
        TrainingStorageImpl(trainingDao)
}