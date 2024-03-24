package com.yankin.training.impl.di

import com.yankin.training.api.usecases.ClearTrainingDeleteQueueUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingAscStreamUseCase
import com.yankin.training.api.usecases.GetCurrentTrainingDescStreamUseCase
import com.yankin.training.api.usecases.GetTrainingByIdUseCase
import com.yankin.training.api.usecases.SaveTrainingUseCase
import com.yankin.training.api.usecases.UpdateTrainingDeleteQueueUseCase
import com.yankin.training.api.usecases.UpdateTrainingUseCase
import com.yankin.training.impl.data.TrainingRepositoryImpl
import com.yankin.training.impl.domain.repositories.TrainingRepository
import com.yankin.training.impl.domain.usecases.ClearTrainingDeleteQueueUseCaseImpl
import com.yankin.training.impl.domain.usecases.GetCurrentTrainingAscStreamUseCaseImpl
import com.yankin.training.impl.domain.usecases.GetCurrentTrainingDescStreamUseCaseImpl
import com.yankin.training.impl.domain.usecases.GetTrainingByIdUseCaseImpl
import com.yankin.training.impl.domain.usecases.SaveTrainingUseCaseImpl
import com.yankin.training.impl.domain.usecases.UpdateTrainingDeleteQueueUseCaseImpl
import com.yankin.training.impl.domain.usecases.UpdateTrainingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TrainingModule {

    @Binds
    fun bindsUpdateTrainingUseCase(
        updateTrainingUseCaseImpl: UpdateTrainingUseCaseImpl
    ): UpdateTrainingUseCase

    @Binds
    fun bindsSaveTrainingUseCase(
        saveTrainingUseCaseImpl: SaveTrainingUseCaseImpl
    ): SaveTrainingUseCase

    @Binds
    fun bindsGetTrainingByIdUseCase(
        getTrainingByIdUseCaseImpl: GetTrainingByIdUseCaseImpl
    ): GetTrainingByIdUseCase

    @Binds
    fun bindsGetCurrentTrainingDescStreamUseCase(
        getCurrentTrainingDescStreamUseCaseImpl: GetCurrentTrainingDescStreamUseCaseImpl
    ): GetCurrentTrainingDescStreamUseCase

    @Binds
    fun bindsGetCurrentTrainingAscStreamUseCase(
        getCurrentTrainingAscStreamUseCaseImpl: GetCurrentTrainingAscStreamUseCaseImpl
    ): GetCurrentTrainingAscStreamUseCase

    @Binds
    fun bindsUpdateTrainingDeleteQueueUseCase(
        updateTrainingDeleteQueueUseCaseImpl: UpdateTrainingDeleteQueueUseCaseImpl
    ): UpdateTrainingDeleteQueueUseCase

    @Binds
    fun bindsClearTrainingDeleteQueueUseCase(
        clearTrainingDeleteQueueUseCaseImpl: ClearTrainingDeleteQueueUseCaseImpl
    ): ClearTrainingDeleteQueueUseCase

    @Binds
    fun bindsTrainingRepository(trainingRepositoryImpl: TrainingRepositoryImpl): TrainingRepository
}