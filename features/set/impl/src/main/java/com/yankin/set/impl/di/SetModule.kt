package com.yankin.set.impl.di

import com.yankin.set.api.usecases.DeleteSetUseCase
import com.yankin.set.api.usecases.GetSetListUseCase
import com.yankin.set.api.usecases.GetCurrentSetStreamUseCase
import com.yankin.set.api.usecases.SaveSetUseCase
import com.yankin.set.impl.data.SetRepositoryImpl
import com.yankin.set.impl.domain.repositories.SetRepository
import com.yankin.set.impl.domain.usecases.DeleteSetUseCaseImpl
import com.yankin.set.impl.domain.usecases.GetSetListUseCaseImpl
import com.yankin.set.impl.domain.usecases.GetCurrentSetStreamUseCaseImpl
import com.yankin.set.impl.domain.usecases.SaveSetUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SetModule {

    @Binds
    fun bindsDeleteSetUseCase(
        deleteSetUseCaseImpl: DeleteSetUseCaseImpl
    ): DeleteSetUseCase

    @Binds
    fun bindsGetSetListUseCase(
        getSetListUseCaseImpl: GetSetListUseCaseImpl
    ): GetSetListUseCase

    @Binds
    fun bindsGetCurrentSetStreamUseCase(
        getCurrentSetStreamUseCaseImpl: GetCurrentSetStreamUseCaseImpl
    ): GetCurrentSetStreamUseCase

    @Binds
    fun bindsSaveSetUseCase(
        saveSetUseCaseImpl: SaveSetUseCaseImpl
    ): SaveSetUseCase

    @Binds
    fun bindsSetRepository(setRepositoryImpl: SetRepositoryImpl): SetRepository
}