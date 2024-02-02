package com.yankin.common.coroutines

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.coroutineScope

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive

inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycle: Lifecycle,
    lifecycleScope: LifecycleCoroutineScope,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job = lifecycleScope.launch {
    flowWithLifecycle(lifecycle, minActiveState).collect { value ->
        action(value)
    }
}

inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { value ->
        action(value)
    }
}

inline fun <reified T> Flow<T>.observeWithLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job {
    val lifecycleOwner = fragment.viewLifecycleOwner
    return lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { value ->
            action(value)
        }
    }
}

inline fun <reified T> Flow<T>.observeWithLifecycleLatest(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
): Job = observeWithLifecycleLatest(
    lifecycle = fragment.viewLifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    action = action
)

inline fun <reified T> Flow<T>.observeWithLifecycleLatest(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit,
): Job = lifecycle.coroutineScope.launch {
    flowWithLifecycle(lifecycle, minActiveState).collectLatest { value ->
        if (isActive) action(value)
    }
}

inline fun <reified T> Flow<T>.observeWithLifecycleWithoutAction(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Job = fragment.viewLifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, minActiveState).collect()
}