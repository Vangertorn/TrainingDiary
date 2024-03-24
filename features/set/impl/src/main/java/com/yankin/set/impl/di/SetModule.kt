package com.yankin.set.impl.di

import com.yankin.set.api.usecases.DeleteSetByIdUseCase
import com.yankin.set.api.usecases.GetSetListUseCase
import com.yankin.set.api.usecases.GetSetListStreamUseCase
import com.yankin.set.api.usecases.SaveSetUseCase
import com.yankin.set.impl.data.SetRepositoryImpl
import com.yankin.set.impl.domain.repositories.SetRepository
import com.yankin.set.impl.domain.usecases.DeleteSetByIdByIdUseCaseImpl
import com.yankin.set.impl.domain.usecases.GetSetListUseCaseImpl
import com.yankin.set.impl.domain.usecases.GetSetListStreamUseCaseImpl
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
        deleteSetByIdUseCaseImpl: DeleteSetByIdByIdUseCaseImpl
    ): DeleteSetByIdUseCase

    @Binds
    fun bindsGetSetListUseCase(
        getSetListUseCaseImpl: GetSetListUseCaseImpl
    ): GetSetListUseCase

    @Binds
    fun bindsSaveSetUseCase(
        saveSetUseCaseImpl: SaveSetUseCaseImpl
    ): SaveSetUseCase

    @Binds
    fun bindsGetSetListStreamUseCase(
        getSetListStreamUseCaseImpl: GetSetListStreamUseCaseImpl
    ): GetSetListStreamUseCase


    @Binds
    fun bindsSetRepository(setRepositoryImpl: SetRepositoryImpl): SetRepository
}