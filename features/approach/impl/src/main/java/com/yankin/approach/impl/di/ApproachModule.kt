package com.yankin.approach.impl.di

import com.yankin.approach.api.usecases.DeleteApproachUseCase
import com.yankin.approach.api.usecases.GetApproachListUseCase
import com.yankin.approach.api.usecases.GetCurrentApproachStreamUseCase
import com.yankin.approach.api.usecases.SaveApproachUseCase
import com.yankin.approach.impl.data.ApproachRepositoryImpl
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import com.yankin.approach.impl.domain.usecases.DeleteApproachUseCaseImpl
import com.yankin.approach.impl.domain.usecases.GetApproachListUseCaseImpl
import com.yankin.approach.impl.domain.usecases.GetCurrentApproachStreamUseCaseImpl
import com.yankin.approach.impl.domain.usecases.SaveApproachUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ApproachModule {

    @Binds
    fun bindsDeleteApproachUseCase(
        deleteApproachUseCaseImpl: DeleteApproachUseCaseImpl
    ): DeleteApproachUseCase

    @Binds
    fun bindsGetApproachListUseCase(
        getApproachListUseCaseImpl: GetApproachListUseCaseImpl
    ): GetApproachListUseCase

    @Binds
    fun bindsGetCurrentApproachStreamUseCase(
        getCurrentApproachStreamUseCaseImpl: GetCurrentApproachStreamUseCaseImpl
    ): GetCurrentApproachStreamUseCase

    @Binds
    fun bindsSaveApproachUseCase(
        saveApproachUseCaseImpl: SaveApproachUseCaseImpl
    ): SaveApproachUseCase

    @Binds
    fun bindsApproachRepository(approachRepositoryImpl: ApproachRepositoryImpl): ApproachRepository
}