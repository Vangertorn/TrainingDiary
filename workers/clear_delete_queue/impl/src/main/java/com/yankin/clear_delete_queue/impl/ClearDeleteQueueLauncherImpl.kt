package com.yankin.clear_delete_queue.impl

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yankin.clear_delete_queue.api.ClearDeleteQueueLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClearDeleteQueueLauncherImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) : ClearDeleteQueueLauncher {
    override fun launch() {
        WorkManager.getInstance(applicationContext.applicationContext).enqueueUniqueWork(
            ClearDeleteQueueWorker.TAG,
            ExistingWorkPolicy.KEEP,
            OneTimeWorkRequestBuilder<ClearDeleteQueueWorker>()
                .addTag(ClearDeleteQueueWorker.TAG)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresCharging(true)
                        .build()
                )
                .build()
        )
    }
}