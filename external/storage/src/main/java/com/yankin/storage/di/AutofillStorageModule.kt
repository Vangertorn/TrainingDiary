package com.yankin.storage.di

import com.yankin.storage.AutofillStorage
import com.yankin.storage.impl.AutofillStorageImpl
import com.yankin.storage.room.PlannerDatabase
import com.yankin.storage.room.dao.ExerciseAutofillDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object AutofillStorageModule {

    @Provides
    @Singleton
    fun provideExerciseAutofillDao(plannerDatabase: PlannerDatabase): ExerciseAutofillDao =
        plannerDatabase.exerciseAutofillDao()

    @Provides
    @Singleton
    fun provideAutofillStorage(autofillDao: ExerciseAutofillDao): AutofillStorage =
        AutofillStorageImpl(autofillDao)
}