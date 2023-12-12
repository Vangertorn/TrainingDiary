package com.yankin.di

import com.yankin.impl.AutofillStorageImpl
import com.yankin.room.PlannerDatabase
import com.yankin.room.dao.ExerciseAutofillDao
import com.yankin.storage.AutofillStorage
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