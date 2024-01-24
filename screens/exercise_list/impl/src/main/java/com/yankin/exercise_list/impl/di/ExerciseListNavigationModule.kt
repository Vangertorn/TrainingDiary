package com.yankin.exercise_list.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.exercise_list.impl.navigation.ExerciseListNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface ExerciseListNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(exerciseListNavigationNode: ExerciseListNavigationNode): NavigationNode
}