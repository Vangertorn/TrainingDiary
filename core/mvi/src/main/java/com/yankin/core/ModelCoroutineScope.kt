package com.yankin.core

import com.yankin.coroutine.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ModelCoroutineScope(dispatchers: CoroutineDispatchers) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + dispatchers.main
}
