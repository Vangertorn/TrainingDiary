package com.yankin.core.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class Middleware<Effect, State, SideEffect> {

    abstract fun invoke(effects: Flow<Effect>, states: Flow<State>): Flow<Effect>

    var middlewareSideEffectFlow: MutableSharedFlow<SideEffect>? = null

    suspend fun emitSideEffect(effect: SideEffect) {
        middlewareSideEffectFlow?.emit(effect)
    }
}
