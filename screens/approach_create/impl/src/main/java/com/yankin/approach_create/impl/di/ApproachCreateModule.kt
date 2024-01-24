package com.yankin.approach_create.impl.di

import com.yankin.approach_create.api.navigation.ApproachCreateCommunicator
import com.yankin.approach_create.impl.navigation.ApproachCreateCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface ApproachCreateModule {
    @Binds
    fun bindCommunicator(approachCreateCommunicatorImpl: ApproachCreateCommunicatorImpl): ApproachCreateCommunicator
}