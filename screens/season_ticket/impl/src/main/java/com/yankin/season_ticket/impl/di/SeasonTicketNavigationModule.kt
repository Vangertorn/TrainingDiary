package com.yankin.season_ticket.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.season_ticket.impl.navigation.SeasonTicketNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface SeasonTicketNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(seasonTicketNavigationNode: SeasonTicketNavigationNode): NavigationNode
}