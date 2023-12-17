package com.yankin.core.mvi

import com.yankin.core.Model
import com.yankin.coroutine.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class StoreData<Action, Effect, State, SideEffect>(
    val middleware: Set<Middleware<Effect, State, SideEffect>>,
    val initState: State,
    val initEffects: List<Effect>,
    val reducer: Reducer<Effect, State>,
    val dispatchers: CoroutineDispatchers,
    val actionToEffect: (Action) -> Effect,
    val errorHandler: suspend FlowCollector<Effect>.(Throwable) -> Unit = {}
)

@ExperimentalCoroutinesApi
class Store<Action, Effect, State, SideEffect>(
    private val coroutineScope: CoroutineScope,
    data: StoreData<Action, Effect, State, SideEffect>,
    effectsBufferSize: Int
) {
    private val state = MutableStateFlow(data.initState)
    private val actions = MutableSharedFlow<Action>(
        extraBufferCapacity = BUFFER_SIZE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val effects = MutableSharedFlow<Effect>(
        extraBufferCapacity = BUFFER_SIZE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = effectsBufferSize
    )
    private val sideEffects = MutableSharedFlow<SideEffect>(
        extraBufferCapacity = BUFFER_SIZE,
        onBufferOverflow = BufferOverflow.SUSPEND,
        replay = effectsBufferSize
    )
    private val mutex = Mutex()

    val stateFlow: StateFlow<State> = state.asStateFlow()

    val sideEffectFlow: SharedFlow<SideEffect> = sideEffects.asSharedFlow()

    init {
        val flow = channelFlow {
            data.initEffects.forEach { send(it) }

            launch {
                data.middleware.map {
                    subscribeMiddleware(data, it)
                }.merge().collect {
                    send(it)
                    effects.emit(it)
                }
            }

            actions.map {
                data.actionToEffect(it)
            }.collect {
                send(it)
                effects.emit(it)
            }

            awaitClose()
        }.onEach {
            mutex.withLock {
                state.value = data.reducer(it, state.value)
            }
        }.flowOn(data.dispatchers.default)

        coroutineScope.launch {
            flow.collect()
        }
    }

    fun action(action: Action) {
        coroutineScope.launch {
            actions.emit(action)
        }
    }

    fun emitSideEffect(direction: SideEffect) {
        coroutineScope.launch {
            sideEffects.emit(direction)
        }
    }

    private fun subscribeMiddleware(
        data: StoreData<Action, Effect, State, SideEffect>,
        middleware: Middleware<Effect, State, SideEffect>,
        initEffect: Boolean = true
    ): Flow<Effect> {
        middleware.middlewareSideEffectFlow = sideEffects
        return middleware.invoke(
            merge(
                flow {
                    data.initEffects.forEach { emit(it) }
                }.takeIf { initEffect } ?: emptyFlow(),
                effects
            ),
            state
        ).catch { e ->
            data.errorHandler(this, e)
            // переподписываемся на middleware в котором произошла ошибка
            // т.к. получивший ошибку flow - находится в терминальном состоянии
            emitAll(subscribeMiddleware(data, middleware, false))
        }
    }
}

@ExperimentalCoroutinesApi
fun <State, Effect, Action, SideEffect> Model<State, Action, SideEffect>.makeStore(
    data: StoreData<Action, Effect, State, SideEffect>,
    effectsBufferSize: Int = 0
) = Store(this, data, effectsBufferSize)

internal const val BUFFER_SIZE = 64
