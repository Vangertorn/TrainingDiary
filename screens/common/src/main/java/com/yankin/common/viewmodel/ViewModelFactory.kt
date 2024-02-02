package com.yankin.common.viewmodel

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class AssistedViewModelFactory<out VM : ViewModel, PARAMS : Parcelable>(
    private val params: PARAMS,
    private val factory: ViewModelFactory<VM, PARAMS>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <VM : ViewModel> create(key: String, modelClass: Class<VM>, handle: SavedStateHandle): VM {
        return factory.create(params = params, handle = handle) as VM
    }
}

interface ViewModelFactory<VM : ViewModel, Params> {
    fun create(params: Params, handle: SavedStateHandle): VM
}