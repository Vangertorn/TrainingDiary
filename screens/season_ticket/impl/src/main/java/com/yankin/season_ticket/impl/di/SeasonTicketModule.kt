package com.yankin.season_ticket.impl.di

import com.yankin.season_ticket.api.navigation.SeasonTicketCommunicator
import com.yankin.season_ticket.impl.navigation.SeasonTicketCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface SeasonTicketModule {
    @Binds
    fun bindCommunicator(seasonTicketCommunicatorImpl: SeasonTicketCommunicatorImpl): SeasonTicketCommunicator
}