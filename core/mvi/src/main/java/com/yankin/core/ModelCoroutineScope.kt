package com.yankin.core

import com.yankin.coroutine.extentions.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ModelCoroutineScope(dispatchers: AppDispatchers) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + dispatchers.main
}
