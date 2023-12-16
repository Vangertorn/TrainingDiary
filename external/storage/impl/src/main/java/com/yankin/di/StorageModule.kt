package com.yankin.di

import android.content.Context
import androidx.room.Room
import com.yankin.room.DATABASE_NAME
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.MuscleGroupDao
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
    fun provideSuperSetDao(plannerDatabase: PlannerDatabase): MuscleGroupDao =
        plannerDatabase.muscleGroupDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlannerDatabase {
        return Room.databaseBuilder(context, PlannerDatabase::class.java, DATABASE_NAME).build()
    }
}