package com.yankin.exercise_name.impl.di

import com.yankin.exercise_name.impl.data.ExercisePatternRepositoryImpl
import com.yankin.exercise_name.impl.domain.repositories.ExercisePatternRepository
import com.yankin.exercise_name.impl.domain.usecases.DeleteExercisePatternUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.GetCurrentExercisePatternAsStringStreamUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.GetCurrentExercisePatternStreamUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.GetExercisePatternByIdUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.SaveExercisePatternUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.UpdateExercisePatternByNameUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.UpdateExercisePatternUseCaseImpl
import com.yankin.exercise_pattern.api.usecases.DeleteExercisePatternUseCase
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternAsStringStreamUseCase
import com.yankin.exercise_pattern.api.usecases.GetCurrentExercisePatternStreamUseCase
import com.yankin.exercise_pattern.api.usecases.GetExercisePatternByIdUseCase
import com.yankin.exercise_pattern.api.usecases.SaveExercisePatternUseCase
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternByNameUseCase
import com.yankin.exercise_pattern.api.usecases.UpdateExercisePatternUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ExercisePatternModule {

    @Binds
    fun bindsDeleteExercisePatternUseCase(
        deleteExercisePatternUseCaseImpl: DeleteExercisePatternUseCaseImpl
    ): DeleteExercisePatternUseCase

    @Binds
    fun bindsGetCurrentExercisePatternAsStringStreamUseCase(
        getCurrentExercisePatternAsStringStreamUseCaseImpl: GetCurrentExercisePatternAsStringStreamUseCaseImpl
    ): GetCurrentExercisePatternAsStringStreamUseCase

    @Binds
    fun bindsGetCurrentExercisePatternStreamUseCase(
        getCurrentExercisePatternStreamUseCaseImpl: GetCurrentExercisePatternStreamUseCaseImpl
    ): GetCurrentExercisePatternStreamUseCase

    @Binds
    fun bindsSaveExercisePatternUseCase(
        saveExercisePatternUseCaseImpl: SaveExercisePatternUseCaseImpl
    ): SaveExercisePatternUseCase

    @Binds
    fun bindsUpdateExercisePatternUseCase(
        updateExercisePatternUseCaseImpl: UpdateExercisePatternUseCaseImpl
    ): UpdateExercisePatternUseCase

    @Binds
    fun bindsGetExercisePatternByIdUseCase(
        getExercisePatternByIdUseCaseImpl: GetExercisePatternByIdUseCaseImpl
    ): GetExercisePatternByIdUseCase

    @Binds
    fun bindsUpdateExercisePatternByNameUseCase(
        updateExercisePatternByNameUseCaseImpl: UpdateExercisePatternByNameUseCaseImpl
    ): UpdateExercisePatternByNameUseCase

    @Binds
    fun bindsExercisePatternRepository(
        exercisePatternRepositoryImpl: ExercisePatternRepositoryImpl
    ): ExercisePatternRepository
}