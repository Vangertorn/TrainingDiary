package com.yankin.exercise.impl.di

import com.yankin.exercise.api.usecases.GetExerciseByIdStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.exercise.impl.data.ExerciseRepositoryImpl
import com.yankin.exercise.api.repositories.ExerciseRepository
import com.yankin.exercise.impl.domain.usecases.GetExerciseByIdStreamUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.GetExerciseListBySuperSetIdStreamUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.UpdateExerciseUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ExerciseModule {

    @Binds
    fun bindsGetExerciseListBySuperSetIdStreamUseCase(
        getExerciseListBySuperSetIdStreamUseCaseImpl: GetExerciseListBySuperSetIdStreamUseCaseImpl
    ): GetExerciseListBySuperSetIdStreamUseCase

    @Binds
    fun bindsUpdateExerciseUseCase(
        updateExerciseUseCaseImpl: UpdateExerciseUseCaseImpl
    ): UpdateExerciseUseCase

    @Binds
    fun bindsGetExerciseByIdStreamUseCase(
        getExerciseByIdStreamUseCaseImpl: GetExerciseByIdStreamUseCaseImpl
    ): GetExerciseByIdStreamUseCase

    @Binds
    fun bindsExerciseRepository(exerciseRepositoryImpl: ExerciseRepositoryImpl): ExerciseRepository
}