package com.yankin.membership.impl.di

import com.yankin.membership.api.usecases.AddMembershipUseCase
import com.yankin.membership.api.usecases.AddTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.DeleteMembershipByIdUseCase
import com.yankin.membership.api.usecases.DeleteTrainingIdFromMembershipUseCase
import com.yankin.membership.api.usecases.GetActiveMembershipStreamUseCase
import com.yankin.membership.impl.data.MembershipRepositoryImpl
import com.yankin.membership.impl.domain.repositories.MembershipRepository
import com.yankin.membership.impl.domain.usecases.AddMembershipUseCaseImpl
import com.yankin.membership.impl.domain.usecases.AddTrainingIdFromMembershipUseCaseImpl
import com.yankin.membership.impl.domain.usecases.DeleteMembershipByIdUseCaseImpl
import com.yankin.membership.impl.domain.usecases.DeleteTrainingIdFromMembershipUseCaseImpl
import com.yankin.membership.impl.domain.usecases.GetActiveMembershipStreamUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MembershipModule {

    @Binds
    fun bindsAddTrainingIdFromMembershipUseCase(
        addTrainingIdFromMembershipUseCaseImpl: AddTrainingIdFromMembershipUseCaseImpl
    ): AddTrainingIdFromMembershipUseCase

    @Binds
    fun bindsDeleteTrainingIdFromMembershipUseCase(
        deleteTrainingIdFromMembershipUseCaseImpl: DeleteTrainingIdFromMembershipUseCaseImpl
    ): DeleteTrainingIdFromMembershipUseCase

    @Binds
    fun bindsAddMembershipUseCase(
        addMembershipUseCaseImpl: AddMembershipUseCaseImpl
    ): AddMembershipUseCase

    @Binds
    fun bindsGetActiveMembershipStreamUseCase(
        getActiveMembershipStreamUseCaseImpl: GetActiveMembershipStreamUseCaseImpl
    ): GetActiveMembershipStreamUseCase

    @Binds
    fun bindsDeleteMembershipByIdUseCase(
        deleteMembershipByIdUseCaseImpl: DeleteMembershipByIdUseCaseImpl
    ): DeleteMembershipByIdUseCase

    @Binds
    fun bindsMembershipRepository(membershipRepositoryImpl: MembershipRepositoryImpl): MembershipRepository
}