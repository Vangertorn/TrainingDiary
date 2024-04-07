package com.yankin.clear_delete_queue.impl

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yankin.training.api.usecases.ClearTrainingDeleteQueueUseCase
import com.yankin.training_block.api.usecases.ClearTrainingBlockDeleteQueueUseCase
import javax.inject.Inject

internal class ClearDeleteQueueWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var clearTrainingDeleteQueueUseCase: ClearTrainingDeleteQueueUseCase

    @Inject
    lateinit var clearTrainingBlockDeleteQueueUseCase: ClearTrainingBlockDeleteQueueUseCase

    override suspend fun doWork(): Result {
        clearTrainingDeleteQueueUseCase.invoke()
        clearTrainingBlockDeleteQueueUseCase.invoke()
        return Result.success()
    }

    companion object {
        const val TAG: String = "ClearDeleteQueueWorker"
    }
}