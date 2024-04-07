package com.yankin.clear_delete_queue.impl

import com.yankin.clear_delete_queue.api.ClearDeleteQueueLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ClearDeleteQueueWorkerModule {
    @Binds
    fun bindLauncher(
        clearDeleteQueueLauncherImpl: ClearDeleteQueueLauncherImpl
    ): ClearDeleteQueueLauncher
}