package com.yankin.trainingdiary.di

import com.yankin.storage.ApproachStorage
import com.yankin.storage.AutofillStorage
import com.yankin.storage.ExerciseStorage
import com.yankin.storage.SuperSetStorage
import com.yankin.storage.TrainingStorage
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.repository.ApproachRepository
import com.yankin.trainingdiary.repository.ExerciseAutofillRepository
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.repository.TrainingRepository
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
    fun provideApproachRepository(
        approachStorage: ApproachStorage,
        exerciseStorage: ExerciseStorage,
        appSettings: AppSettings
    ): ApproachRepository = ApproachRepository(
        approachStorage = approachStorage,
        exerciseStorage = exerciseStorage,
        appSettings = appSettings
    )

    @Provides
    @Singleton
    fun provideExerciseAutofillRepository(
        autofillStorage: AutofillStorage
    ): ExerciseAutofillRepository = ExerciseAutofillRepository(
        autofillStorage = autofillStorage
    )

    @Provides
    @Singleton
    fun provideExerciseRepository(
        exerciseStorage: ExerciseStorage,
        trainingStorage: TrainingStorage,
        appSettings: AppSettings
    ): ExerciseRepository = ExerciseRepository(
        exerciseStorage = exerciseStorage,
        trainingStorage = trainingStorage,
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

    @Provides
    @Singleton
    fun provideTrainingRepository(
        trainingStorage: TrainingStorage,
        appSettings: AppSettings
    ): TrainingRepository = TrainingRepository(
        trainingStorage = trainingStorage,
        appSettings = appSettings
    )
}
