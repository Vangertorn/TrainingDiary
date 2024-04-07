package com.yankin.trainingdiary.di

import com.yankin.clear_delete_queue.api.ClearDeleteQueueLauncher
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WorkersFactory {
    fun getClearDeleteQueueLauncher(): ClearDeleteQueueLauncher
}