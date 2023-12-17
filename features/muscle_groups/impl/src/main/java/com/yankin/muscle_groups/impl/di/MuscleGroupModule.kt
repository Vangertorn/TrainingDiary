package com.yankin.muscle_groups.impl.di

import com.yankin.muscle_groups.api.usecases.DeleteMuscleGroupUseCase
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.GetCurrentMuscleGroupStreamUseCase
import com.yankin.muscle_groups.api.usecases.RecoverDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveMuscleGroupUseCase
import com.yankin.muscle_groups.impl.data.MuscleGroupRepositoryImpl
import com.yankin.muscle_groups.impl.domain.repositories.MuscleGroupRepository
import com.yankin.muscle_groups.impl.domain.usecases.DeleteMuscleGroupUseCaseImpl
import com.yankin.muscle_groups.impl.domain.usecases.GetAllMuscleGroupListUseCaseImpl
import com.yankin.muscle_groups.impl.domain.usecases.GetCurrentMuscleGroupStreamUseCaseImpl
import com.yankin.muscle_groups.impl.domain.usecases.RecoverDefaultMuscleGroupListUseCaseImpl
import com.yankin.muscle_groups.impl.domain.usecases.SaveDefaultMuscleGroupListUseCaseImpl
import com.yankin.muscle_groups.impl.domain.usecases.SaveMuscleGroupUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MuscleGroupModule {

    @Binds
    fun bindsGetCurrentMuscleGroupStreamUseCase(
        getCurrentMuscleGroupStreamUseCaseImpl: GetCurrentMuscleGroupStreamUseCaseImpl
    ): GetCurrentMuscleGroupStreamUseCase

    @Binds
    fun bindsSaveMuscleGroupUseCase(
        saveMuscleGroupUseCaseImpl: SaveMuscleGroupUseCaseImpl
    ): SaveMuscleGroupUseCase

    @Binds
    fun bindsSaveDefaultMuscleGroupListUseCase(
        saveDefaultMuscleGroupListUseCaseImpl: SaveDefaultMuscleGroupListUseCaseImpl
    ): SaveDefaultMuscleGroupListUseCase

    @Binds
    fun bindsRecoverDefaultMuscleGroupListUseCase(
        recoverDefaultMuscleGroupListUseCaseImpl: RecoverDefaultMuscleGroupListUseCaseImpl
    ): RecoverDefaultMuscleGroupListUseCase

    @Binds
    fun bindsDeleteMuscleGroupUseCase(
        deleteMuscleGroupUseCaseImpl: DeleteMuscleGroupUseCaseImpl
    ): DeleteMuscleGroupUseCase

    @Binds
    fun bindsGetAllMuscleGroupListUseCase(
        getAllMuscleGroupListUseCaseImpl: GetAllMuscleGroupListUseCaseImpl
    ): GetAllMuscleGroupListUseCase

    @Binds
    fun bindsMuscleGroupRepository(muscleGroupRepositoryImpl: MuscleGroupRepositoryImpl): MuscleGroupRepository
}