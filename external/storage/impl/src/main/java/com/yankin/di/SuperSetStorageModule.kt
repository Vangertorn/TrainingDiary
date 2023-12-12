package com.yankin.di

import com.yankin.impl.SuperSetStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.SuperSetDao
import com.yankin.storage.SuperSetStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object SuperSetStorageModule {

    @Provides
    @Singleton
    fun provideSuperSetDao(plannerDatabase: PlannerDatabase): SuperSetDao =
        plannerDatabase.superSetDao()

    @Provides
    @Singleton
    fun provideSuperSetStorage(superSetDao: SuperSetDao): SuperSetStorage =
        SuperSetStorageImpl(superSetDao)
}