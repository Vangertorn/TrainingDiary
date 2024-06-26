package com.yankin.super_set.impl.di

import com.yankin.super_set.api.usecases.DeleteSuperSetFalseUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetTrueUseCase
import com.yankin.super_set.api.usecases.DeleteSuperSetsUseCase
import com.yankin.super_set.api.usecases.GetCurrentSuperSetListStreamUseCase
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import com.yankin.super_set.api.usecases.UpdateSuperSetVisibleUseCase
import com.yankin.super_set.impl.data.SuperSetRepositoryImpl
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import com.yankin.super_set.impl.domain.usecases.DeleteSuperSetFalseUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.DeleteSuperSetTrueUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.DeleteSuperSetsUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.GetCurrentSuperSetListStreamUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.SaveSuperSetUseCaseImpl
import com.yankin.super_set.impl.domain.usecases.UpdateSuperSetVisibleUseCaseImpl
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
    fun bindsDeleteSuperSetsUseCase(
        deleteSuperSetsUseCaseImpl: DeleteSuperSetsUseCaseImpl
    ): DeleteSuperSetsUseCase

    @Binds
    fun bindsDeleteSuperSetTrueUseCase(
        deleteSuperSetTrueUseCaseImpl: DeleteSuperSetTrueUseCaseImpl
    ): DeleteSuperSetTrueUseCase

    @Binds
    fun bindsGetCurrentSuperSetListStreamUseCase(
        getCurrentSuperSetListStreamUseCaseImpl: GetCurrentSuperSetListStreamUseCaseImpl
    ): GetCurrentSuperSetListStreamUseCase

    @Binds
    fun bindsSaveSuperSetUseCase(
        saveSuperSetUseCaseImpl: SaveSuperSetUseCaseImpl
    ): SaveSuperSetUseCase

    @Binds
    fun bindsUpdateSuperSetVisibleUseCase(
        updateSuperSetVisibleUseCaseImpl: UpdateSuperSetVisibleUseCaseImpl
    ): UpdateSuperSetVisibleUseCase

    @Binds
    fun bindsSuperSetRepository(superSetRepositoryImpl: SuperSetRepositoryImpl): SuperSetRepository
}