package com.yankin.training_list.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.training_list.impl.navigation.TrainingListNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface TrainingListModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(trainingListNavigationNode: TrainingListNavigationNode): NavigationNode
}