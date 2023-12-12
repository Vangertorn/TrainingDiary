package com.yankin.di

import com.yankin.impl.ApproachStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.ApproachDao
import com.yankin.storage.ApproachStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApproachStorageModule {

    @Provides
    @Singleton
    fun provideApproachDao(plannerDatabase: PlannerDatabase): ApproachDao =
        plannerDatabase.approachDao()

    @Provides
    @Singleton
    fun provideApproachStorage(approachDao: ApproachDao): ApproachStorage =
        ApproachStorageImpl(approachDao)
}