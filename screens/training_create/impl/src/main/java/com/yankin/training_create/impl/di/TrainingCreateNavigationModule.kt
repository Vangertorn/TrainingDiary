package com.yankin.training_create.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.training_create.impl.navigation.TrainingCreateNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface TrainingCreateNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(trainingCreateNavigationNode: TrainingCreateNavigationNode): NavigationNode
}