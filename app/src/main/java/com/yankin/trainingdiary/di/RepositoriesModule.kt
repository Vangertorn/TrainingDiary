package com.yankin.trainingdiary.di

import android.content.Context
import com.yankin.trainingdiary.dao.database.ApproachDao
import com.yankin.trainingdiary.dao.database.ExerciseAutofillDao
import com.yankin.trainingdiary.dao.database.ExerciseDao
import com.yankin.trainingdiary.dao.database.MuscleGroupDao
import com.yankin.trainingdiary.dao.database.SuperSetDao
import com.yankin.trainingdiary.dao.database.TrainingDao
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.repository.ApproachRepository
import com.yankin.trainingdiary.repository.ExerciseAutofillRepository
import com.yankin.trainingdiary.repository.ExerciseRepository
import com.yankin.trainingdiary.repository.MuscleGroupRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoriesModule {

    @Provides
    @Singleton
    fun provideApproachRepository(
        approachDao: ApproachDao,
        exerciseDao: ExerciseDao,
        appSettings: AppSettings
    ): ApproachRepository = ApproachRepository(
        approachDao = approachDao,
        exerciseDao = exerciseDao,
        appSettings = appSettings
    )

    @Provides
    @Singleton
    fun provideExerciseAutofillRepository(
        exerciseAutofillDao: ExerciseAutofillDao
    ): ExerciseAutofillRepository = ExerciseAutofillRepository(
        exerciseAutofillDao = exerciseAutofillDao
    )

    @Provides
    @Singleton
    fun provideExerciseRepository(
        exerciseDao: ExerciseDao,
        trainingDao: TrainingDao,
        appSettings: AppSettings
    ): ExerciseRepository = ExerciseRepository(
        exerciseDao = exerciseDao,
        trainingDao = trainingDao,
        appSettings = appSettings
    )

    @Provides
    @Singleton
    fun provideMuscleGroupRepository(
        muscleGroupDao: MuscleGroupDao,
        @ApplicationContext context: Context
    ): MuscleGroupRepository = MuscleGroupRepository(
        muscleGroupDao = muscleGroupDao,
        context = context
    )

    @Provides
    @Singleton
    fun provideSuperSetRepository(
        superSetDao: SuperSetDao,
        exerciseRepository: ExerciseRepository,
        appSettings: AppSettings,
        exerciseDao: ExerciseDao
    ): SuperSetRepository = SuperSetRepository(
        superSetDao = superSetDao,
        exerciseRepository = exerciseRepository,
        appSettings = appSettings,
        exerciseDao = exerciseDao
    )

    @Provides
    @Singleton
    fun provideTrainingRepository(
        trainingDao: TrainingDao,
        appSettings: AppSettings
    ): TrainingRepository = TrainingRepository(
        trainingDao = trainingDao,
        appSettings = appSettings
    )
}
