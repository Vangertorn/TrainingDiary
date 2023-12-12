package com.yankin.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Model<State, Action, SideEffect> : CoroutineScope {

    val stateFlow: StateFlow<State>
    val sideEffectFlow: SharedFlow<SideEffect>

    fun action(value: Action)
    fun emitSideEffect(effect: SideEffect)
}
