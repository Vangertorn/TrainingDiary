package com.yankin.storage.di

import com.yankin.storage.ApproachStorage
import com.yankin.storage.impl.ApproachStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.ApproachDao
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