package com.yankin.super_set.impl.di

import com.yankin.super_set.api.usecases.DeleteSuperSetFalseUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetTrueUseCase
import com.yankin.super_set.api.usecases.GetTrainingSuperSetListStreamUseCase
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import com.yankin.super_set.impl.data.SuperSetRepositoryImpl
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import com.yankin.super_set.impl.domain.usecases.DeleteSuperSetFalseUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.DeleteSuperSetTrueUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.GetTrainingSuperSetListStreamUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.SaveSuperSetUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ExerciseModule {

    @Binds
    fun bindsDeleteSuperSetFalseUseCase(
        deleteSuperSetFalseUseCaseImpl: DeleteSuperSetFalseUseCaseImpl
    ): DeleteSuperSetFalseUseCase

    @Binds
    fun bindsDeleteSuperSetTrueUseCase(
        deleteSuperSetTrueUseCaseImpl: DeleteSuperSetTrueUseCaseImpl
    ): DeleteSuperSetTrueUseCase

    @Binds
    fun bindsGetTrainingSuperSetListStreamUseCase(
        getTrainingSuperSetListStreamUseCaseImpl: GetTrainingSuperSetListStreamUseCaseImpl
    ): GetTrainingSuperSetListStreamUseCase

    @Binds
    fun bindsSaveSuperSetUseCase(
        saveSuperSetUseCaseImpl: SaveSuperSetUseCaseImpl
    ): SaveSuperSetUseCase

    @Binds
    fun bindsSuperSetRepository(superSetRepositoryImpl: SuperSetRepositoryImpl): SuperSetRepository
}