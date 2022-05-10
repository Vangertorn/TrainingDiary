package com.yankin.trainingdiary.di

import android.content.Context
import com.yankin.trainingdiary.datastore.AppSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {

    @Provides
    @Singleton
    fun provideAppSetting(@ApplicationContext context: Context): AppSettings {
        return AppSettings(context)
    }
}
