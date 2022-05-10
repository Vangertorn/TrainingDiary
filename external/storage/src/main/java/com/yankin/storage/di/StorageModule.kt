package com.yankin.storage.di

import android.content.Context
import androidx.room.Room
import com.yankin.storage.ApproachStorage
import com.yankin.storage.impl.ApproachStorageImpl
import com.yankin.storage.room.DATABASE_NAME
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.ApproachDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlannerDatabase {
        return Room.databaseBuilder(context, PlannerDatabase::class.java, DATABASE_NAME).build()
    }
}