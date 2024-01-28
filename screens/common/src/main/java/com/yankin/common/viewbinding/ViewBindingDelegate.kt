package com.yankin.common.viewbinding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

@JvmName("fragmentViewBindingInflate")
fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (LayoutInflater) -> T,
): ReadOnlyProperty<Fragment, T> = when (this) {
    is DialogFragment -> dialogFragmentViewBinding(viewBindingFactory)
    else -> fragmentViewBinding(viewBindingFactory)
}

@JvmName("fragmentViewBindingBind")
fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T,
): ReadOnlyProperty<Fragment, T> = fragmentViewBinding(viewBindingFactory)

@JvmName("fragmentViewBindingBind")
fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ReadOnlyProperty<Fragment, T> = fragmentViewBinding(viewBindingFactory, viewBindingRootId)

private fun <T : ViewBinding> Fragment.fragmentViewBinding(
    viewBindingFactory: (LayoutInflater) -> T,
) = FragmentInflateViewBindingDelegate(
    fragment = this,
    viewBindingFactory = viewBindingFactory
)

private fun <T : ViewBinding> Fragment.fragmentViewBinding(
    viewBindingFactory: (View) -> T,
) = FragmentViewBindingDelegate(
    fragment = this,
    viewBindingFactory = viewBindingFactory
)

private fun <T : ViewBinding> Fragment.fragmentViewBinding(
    viewBindingFactory: (View) -> T,
    @IdRes viewBindingRootId: Int = 0,
) = FragmentViewBindingDelegate(
    fragment = this,
    viewBindingFactory = viewBindingFactory,
    viewBindingRootId = viewBindingRootId
)

private fun <T : ViewBinding> DialogFragment.dialogFragmentViewBinding(
    viewBindingFactory: (LayoutInflater) -> T,
) = DialogFragmentViewBindingDelegate(
    dialog = this,
    viewBindingFactory = viewBindingFactory
)

inline fun <T : ViewBinding> Activity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

inline fun <T : ViewBinding> View.viewBinding(
    parent: ViewGroup,
    crossinline bindingInflater: (LayoutInflater, ViewGroup) -> T,
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(LayoutInflater.from(context), parent)
}

inline fun <T : ViewBinding> ViewGroup.viewBinding(
    parent: ViewGroup,
    attachToParent: Boolean,
    crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T,
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(LayoutInflater.from(context), parent, attachToParent)
}