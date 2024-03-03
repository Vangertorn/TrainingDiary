package com.yankin.training_exercises.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.training_exercises.impl.navigation.TrainingExercisesNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface TrainingExercisesNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(trainingExercisesNavigationNode: TrainingExercisesNavigationNode): NavigationNode
}