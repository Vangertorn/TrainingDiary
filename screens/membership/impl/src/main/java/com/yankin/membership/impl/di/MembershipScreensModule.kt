package com.yankin.membership.impl.di

import com.yankin.membership.api.navigation.MembershipCommunicator
import com.yankin.membership.impl.navigation.MembershipCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface MembershipScreensModule {
    @Binds
    fun bindCommunicator(membershipCommunicatorImpl: MembershipCommunicatorImpl): MembershipCommunicator
}