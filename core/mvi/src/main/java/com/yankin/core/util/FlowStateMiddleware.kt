package com.yankin.core.util

import com.yankin.core.mvi.Middleware
import com.yankin.coroutine.extentions.AppDispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@FlowPreview
class FlowStateMiddleware<Effect, State, SideEffect, T>(
    val dispatchers: AppDispatchers,
    val flow: Flow<T>,
    val initEffect: Effect,
    val resultEffectFactory: (result: T) -> Effect,
    val resultSideEffectFactory: ((result: T) -> SideEffect?)? = null
) : Middleware<Effect, State, SideEffect>() {

    override fun invoke(
        effects: Flow<Effect>,
        states: Flow<State>
    ) = effects
        .filter {
            initEffect!!::class == it!!::class
        }
        .flatMapMerge {
            flow
        }
        .distinctUntilChanged()
        .onEach { result ->
            resultSideEffectFactory?.let {
                it(result)?.let { sideEffect ->
                    emitSideEffect(sideEffect)
                }
            }
        }
        .map { result ->
            resultEffectFactory(result)
        }
        .flowOn(dispatchers.io)
}
