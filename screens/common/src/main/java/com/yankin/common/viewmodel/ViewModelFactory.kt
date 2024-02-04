package com.yankin.common.viewmodel

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

@Suppress("UNCHECKED_CAST")
class AssistedParamsViewModelFactory<out VM : ViewModel, PARAMS : Parcelable>(
    private val params: PARAMS,
    private val factory: ViewModelFactory.ViewModelParamsFactory<VM, PARAMS>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <VM : ViewModel> create(key: String, modelClass: Class<VM>, handle: SavedStateHandle): VM {
        return factory.create(params = params, handle = handle) as VM
    }
}

@Suppress("UNCHECKED_CAST")
class AssistedViewModelFactory<out VM : ViewModel>(
    private val factory: ViewModelFactory.ViewModelWithoutParamsFactory<VM>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <VM : ViewModel> create(key: String, modelClass: Class<VM>, handle: SavedStateHandle): VM {
        return factory.create(handle = handle) as VM
    }
}

sealed interface ViewModelFactory<VM : ViewModel> {

    interface ViewModelParamsFactory<VM : ViewModel, Params> : ViewModelFactory<VM> {
        fun create(params: Params, handle: SavedStateHandle): VM
    }

    interface ViewModelWithoutParamsFactory<VM : ViewModel> : ViewModelFactory<VM> {
        fun create(handle: SavedStateHandle): VM
    }
}