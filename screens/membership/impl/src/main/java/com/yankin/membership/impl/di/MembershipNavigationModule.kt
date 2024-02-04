package com.yankin.membership.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.membership.impl.navigation.MembershipNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface MembershipNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(membershipNavigationNode: MembershipNavigationNode): NavigationNode
}