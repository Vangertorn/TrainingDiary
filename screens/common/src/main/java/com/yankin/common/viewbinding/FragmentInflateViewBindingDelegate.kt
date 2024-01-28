package com.yankin.common.viewbinding

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.yankin.common.lifecycle.DefaultLifecycleObserverImpl
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class FragmentInflateViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (LayoutInflater) -> T,
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }
        val binding = viewBindingFactory(LayoutInflater.from(fragment.requireContext()))
        this.binding = binding
        clearViewBindingOnDestroy(fragment.viewLifecycleOwner.lifecycle)
        return binding
    }

    @MainThread
    private fun clearViewBindingOnDestroy(lifecycle: Lifecycle) {
        lifecycle.addObserver(
            DefaultLifecycleObserverImpl(
                destroy = { _, observer ->
                    lifecycle.removeObserver(observer)
                    mainHandler.post { binding = null }
                }
            )
        )
    }
}