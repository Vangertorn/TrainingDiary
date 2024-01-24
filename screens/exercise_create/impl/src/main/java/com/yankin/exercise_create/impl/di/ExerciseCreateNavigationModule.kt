package com.yankin.exercise_create.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.exercise_create.impl.navigation.ExerciseCreateNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface ExerciseCreateNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(exerciseCreateNavigationNode: ExerciseCreateNavigationNode): NavigationNode
}