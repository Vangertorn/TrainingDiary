package com.yankin.common.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel : ViewModel(), CoroutineScope {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
