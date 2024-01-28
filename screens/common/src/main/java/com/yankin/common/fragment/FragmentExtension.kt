package com.yankin.common.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.yankin.common.lifecycle.DefaultLifecycleObserverImpl

fun Fragment.onBackPressed(owner: LifecycleOwner, action: () -> Unit) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = action()
    }
    owner.lifecycle.addObserver(
        DefaultLifecycleObserverImpl(
            resume = { currentOwner, _ ->
                requireActivity().onBackPressedDispatcher.addCallback(currentOwner, callback)
            },
            pause = { _, _ ->
                callback.remove()
            }
        ))
}

fun Fragment.onBackPressed(action: () -> Unit) {
    onBackPressed(viewLifecycleOwner, action)
}