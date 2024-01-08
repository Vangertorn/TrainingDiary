package com.yankin.trainingdiary.di

import com.yankin.storage.SuperSetStorage
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoriesModule {

    @Provides
    @Singleton
    fun provideExerciseRepository(
        exerciseStorage: ExerciseStorage,
        appSettings: AppSettings
    ): ExerciseRepository = ExerciseRepository(
        exerciseStorage = exerciseStorage,
        appSettings = appSettings
    )

    @Provides
    @Singleton
    fun provideSuperSetRepository(
        superSetStorage: SuperSetStorage,
        exerciseRepository: ExerciseRepository,
        appSettings: AppSettings,
        exerciseStorage: ExerciseStorage
    ): SuperSetRepository = SuperSetRepository(
        superSetStorage = superSetStorage,
        exerciseRepository = exerciseRepository,
        appSettings = appSettings,
        exerciseStorage = exerciseStorage
    )
}
