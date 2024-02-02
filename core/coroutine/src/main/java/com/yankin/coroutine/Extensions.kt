package com.yankin.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchJob(
    catchBlock: (t: Throwable) -> Unit,
    finallyBlock: (() -> Unit)? = null,
    context: CoroutineContext? = null,
    tryBlock: suspend CoroutineScope.() -> Unit
): Job {
    return context?.let { notNullContext ->
        launch(notNullContext + BaseCoroutineExceptionHandler(catchBlock)) {
            try {
                tryBlock()
            } finally {
                finallyBlock?.invoke()
            }
        }
    } ?: run {
        launch(coroutineContext + BaseCoroutineExceptionHandler(catchBlock)) {
            try {
                tryBlock()
            } finally {
                finallyBlock?.invoke()
            }
        }
    }
}

fun <T> Flow<T>.launchInJob(
    scope: CoroutineScope,
    catchBlock: suspend (t: Throwable) -> Unit,
): Job = this
    .catch { throwable -> catchBlock.invoke(throwable) }
    .launchIn(scope)

private class BaseCoroutineExceptionHandler(
    private val errorCallback: ((Throwable) -> Unit)
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        errorCallback(exception)
    }
}