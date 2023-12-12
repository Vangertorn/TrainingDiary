package com.yankin.di

import com.yankin.impl.TrainingStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.TrainingDao
import com.yankin.storage.TrainingStorage
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