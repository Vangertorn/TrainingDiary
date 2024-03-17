package com.yankin.training_block.impl.di

import com.yankin.training_block.api.usecases.ClearTrainingBlockDeleteQueueUseCase
import com.yankin.training_block.api.usecases.GetTrainingBlockByIdStreamUseCase
import com.yankin.training_block.api.usecases.GetTrainingBlockListStreamUseCase
import com.yankin.training_block.api.usecases.SaveTrainingBlockUseCase
import com.yankin.training_block.api.usecases.SwitchTrainingBlockPositionUseCase
import com.yankin.training_block.api.usecases.UpdateTrainingBlockDeleteQueueUseCase
import com.yankin.training_block.impl.data.TrainingBlockRepositoryImpl
import com.yankin.training_block.impl.domain.repositories.TrainingBlockRepository
import com.yankin.training_block.impl.domain.usecases.ClearTrainingBlockDeleteQueueUseCaseImpl
import com.yankin.training_block.impl.domain.usecases.GetTrainingBlockByIdStreamUseCaseImpl
import com.yankin.training_block.impl.domain.usecases.GetTrainingBlockListStreamUseCaseImpl
import com.yankin.training_block.impl.domain.usecases.SaveTrainingBlockUseCaseImpl
import com.yankin.training_block.impl.domain.usecases.SwitchTrainingBlockPositionUseCaseImpl
import com.yankin.training_block.impl.domain.usecases.UpdateTrainingBlockDeleteQueueUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TrainingBlockModule {

    @Binds
    fun bindsUpdateTrainingBlockDeleteQueueUseCase(
        updateTrainingBlockDeleteQueueUseCaseImpl: UpdateTrainingBlockDeleteQueueUseCaseImpl
    ): UpdateTrainingBlockDeleteQueueUseCase

    @Binds
    fun bindsClearTrainingBlockDeleteQueueUseCase(
        clearTrainingBlockDeleteQueueUseCaseImpl: ClearTrainingBlockDeleteQueueUseCaseImpl
    ): ClearTrainingBlockDeleteQueueUseCase

    @Binds
    fun bindsGetTrainingBlockListStreamUseCase(
        getTrainingBlockListStreamUseCaseImpl: GetTrainingBlockListStreamUseCaseImpl
    ): GetTrainingBlockListStreamUseCase

    @Binds
    fun bindsSaveTrainingBlockUseCase(
        saveTrainingBlockUseCaseImpl: SaveTrainingBlockUseCaseImpl
    ): SaveTrainingBlockUseCase

    @Binds
    fun bindsSwitchTrainingBlockPositionUseCase(
        switchTrainingBlockPositionUseCaseImpl: SwitchTrainingBlockPositionUseCaseImpl
    ): SwitchTrainingBlockPositionUseCase

    @Binds
    fun bindsGetTrainingBlockByIdStreamUseCase(
        getTrainingBlockByIdStreamUseCaseImpl: GetTrainingBlockByIdStreamUseCaseImpl
    ): GetTrainingBlockByIdStreamUseCase

    @Binds
    fun bindsTrainingBlockRepository(trainingBlockRepositoryImpl: TrainingBlockRepositoryImpl): TrainingBlockRepository
}