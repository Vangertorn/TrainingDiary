package com.yankin.storage.di

import com.yankin.storage.MuscleGroupStorage
import com.yankin.storage.impl.MuscleGroupStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.MuscleGroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object MuscleGroupStorageModule {

    @Provides
    @Singleton
    fun provideMuscleGroupDao(plannerDatabase: PlannerDatabase): com.yankin.storage.room.dao.MuscleGroupDao =
        plannerDatabase.muscleGroupDao()

    @Provides
    @Singleton
    fun provideMuscleGroupStorage(muscleGroupDao: MuscleGroupDao): MuscleGroupStorage =
        MuscleGroupStorageImpl(muscleGroupDao)
}