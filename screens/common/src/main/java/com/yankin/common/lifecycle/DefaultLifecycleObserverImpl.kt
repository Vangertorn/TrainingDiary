package com.yankin.common.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

class DefaultLifecycleObserverImpl(
    private val create: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->},
    private val start: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->},
    private val resume: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->},
    private val pause: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->},
    private val stop: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->},
    private val destroy: (owner: LifecycleOwner, observer: LifecycleObserver) -> Unit = {_, _ ->}
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) = create.invoke(owner, this)
    override fun onStart(owner: LifecycleOwner) = start.invoke(owner, this)
    override fun onResume(owner: LifecycleOwner) = resume.invoke(owner, this)
    override fun onPause(owner: LifecycleOwner) = pause.invoke(owner, this)
    override fun onStop(owner: LifecycleOwner) = stop.invoke(owner, this)
    override fun onDestroy(owner: LifecycleOwner) = destroy.invoke(owner, this)
}