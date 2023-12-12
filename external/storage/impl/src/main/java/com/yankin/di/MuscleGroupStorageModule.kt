package com.yankin.di

import com.yankin.impl.MuscleGroupStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.MuscleGroupDao
import com.yankin.storage.MuscleGroupStorage
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
    fun provideMuscleGroupDao(plannerDatabase: PlannerDatabase): MuscleGroupDao =
        plannerDatabase.muscleGroupDao()

    @Provides
    @Singleton
    fun provideMuscleGroupStorage(muscleGroupDao: MuscleGroupDao): MuscleGroupStorage =
        MuscleGroupStorageImpl(muscleGroupDao)
}