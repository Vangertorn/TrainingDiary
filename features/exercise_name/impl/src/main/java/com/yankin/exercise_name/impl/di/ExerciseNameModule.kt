package com.yankin.exercise_name.impl.di

import com.yankin.exercese_name.api.usecases.DeleteExerciseNameUseCase
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameAsStringStreamUseCase
import com.yankin.exercese_name.api.usecases.GetCurrentExerciseNameStreamUseCase
import com.yankin.exercese_name.api.usecases.SaveExerciseNameUseCase
import com.yankin.exercese_name.api.usecases.UpdateExerciseNameUseCase
import com.yankin.exercise_name.impl.data.ExerciseNameRepositoryImpl
import com.yankin.exercise_name.impl.domain.repositories.ExerciseNameRepository
import com.yankin.exercise_name.impl.domain.usecases.DeleteExerciseNameUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.GetCurrentExerciseNameAsStringStreamUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.GetCurrentExerciseNameStreamUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.SaveExerciseNameUseCaseImpl
import com.yankin.exercise_name.impl.domain.usecases.UpdateExerciseNameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ExerciseNameModule {

    @Binds
    fun bindsDeleteExerciseNameUseCase(
        deleteExerciseNameUseCaseImpl: DeleteExerciseNameUseCaseImpl
    ): DeleteExerciseNameUseCase

    @Binds
    fun bindsGetCurrentExerciseNameAsStringStreamUseCase(
        getCurrentExerciseNameAsStringStreamUseCaseImpl: GetCurrentExerciseNameAsStringStreamUseCaseImpl
    ): GetCurrentExerciseNameAsStringStreamUseCase

    @Binds
    fun bindsGetCurrentExerciseNameStreamUseCase(
        getCurrentExerciseNameStreamUseCaseImpl: GetCurrentExerciseNameStreamUseCaseImpl
    ): GetCurrentExerciseNameStreamUseCase

    @Binds
    fun bindsSaveExerciseNameUseCase(
        saveExerciseNameUseCaseImpl: SaveExerciseNameUseCaseImpl
    ): SaveExerciseNameUseCase

    @Binds
    fun bindsUpdateExerciseNameUseCase(
        updateExerciseNameUseCaseImpl: UpdateExerciseNameUseCaseImpl
    ): UpdateExerciseNameUseCase

    @Binds
    fun bindsExerciseNameRepository(exerciseNameRepositoryImpl: ExerciseNameRepositoryImpl): ExerciseNameRepository
}