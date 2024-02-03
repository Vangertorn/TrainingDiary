package com.yankin.mvvm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface BaseViewModel<StateModel, UiState, Event> {

    val stateModel: MutableStateFlow<StateModel>
    val eventState: MutableStateFlow<Event>

    fun getUiStream(): Flow<UiState>
    fun getEventStream(): Flow<Event>
}
