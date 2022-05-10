package com.yankin.storage.di

import com.yankin.storage.SuperSetStorage
import com.yankin.storage.impl.SuperSetStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.SuperSetDao
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