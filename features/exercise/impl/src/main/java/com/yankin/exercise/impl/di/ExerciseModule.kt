package com.yankin.exercise.impl.di

import com.yankin.exercise.api.usecases.DeleteEmptyExerciseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseFalseUseCase
import com.yankin.exercise.api.usecases.DeleteExerciseTrueUseCase
import com.yankin.exercise.api.usecases.DeleteExercisesUseCase
import com.yankin.exercise.api.usecases.GetCurrentExerciseListStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseByIdStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdUseCase
import com.yankin.exercise.api.usecases.SaveExerciseUseCase
import com.yankin.exercise.api.usecases.SwitchExercisePositionUseCase
import com.yankin.exercise.api.usecases.UpdateExerciseUseCase
import com.yankin.exercise.impl.data.ExerciseRepositoryImpl
import com.yankin.exercise.impl.domain.repositories.ExerciseRepository
import com.yankin.exercise.impl.domain.usecases.DeleteEmptyExerciseUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.DeleteExerciseFalseUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.DeleteExerciseTrueUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.DeleteExercisesUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.GetCurrentExerciseListStreamUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.GetExerciseByIdStreamUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.GetExerciseListBySuperSetIdStreamUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.GetExerciseListBySuperSetIdUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.SaveExerciseUseCaseImpl
import com.yankin.exercise.impl.domain.usecases.SwitchExercisePositionUseCaseImpl
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
    fun bindsGetExerciseListBySuperSetIdUseCase(
        getExerciseListBySuperSetIdUseCaseImpl: GetExerciseListBySuperSetIdUseCaseImpl
    ): GetExerciseListBySuperSetIdUseCase

    @Binds
    fun bindsDeleteEmptyExerciseUseCase(
        deleteEmptyExerciseUseCaseImpl: DeleteEmptyExerciseUseCaseImpl
    ): DeleteEmptyExerciseUseCase

    @Binds
    fun bindsDeleteExerciseFalseUseCase(
        deleteExerciseFalseUseCaseImpl: DeleteExerciseFalseUseCaseImpl
    ): DeleteExerciseFalseUseCase

    @Binds
    fun bindsDeleteExercisesUseCase(
        deleteExercisesUseCaseImpl: DeleteExercisesUseCaseImpl
    ): DeleteExercisesUseCase

    @Binds
    fun bindsDeleteExerciseTrueUseCase(
        deleteExerciseTrueUseCaseImpl: DeleteExerciseTrueUseCaseImpl
    ): DeleteExerciseTrueUseCase

    @Binds
    fun bindsGetCurrentExerciseListStreamUseCase(
        getCurrentExerciseListStreamUseCaseImpl: GetCurrentExerciseListStreamUseCaseImpl
    ): GetCurrentExerciseListStreamUseCase

    @Binds
    fun bindsSaveExerciseUseCase(
        saveExerciseUseCaseImpl: SaveExerciseUseCaseImpl
    ): SaveExerciseUseCase

    @Binds
    fun bindsSwitchExercisePositionUseCase(
        switchExercisePositionUseCaseImpl: SwitchExercisePositionUseCaseImpl
    ): SwitchExercisePositionUseCase

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