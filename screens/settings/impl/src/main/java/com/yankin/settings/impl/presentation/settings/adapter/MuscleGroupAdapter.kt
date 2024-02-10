package com.yankin.settings.impl.presentation.settings.adapter

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class MuscleGroupAdapter(
    onMuscleGroupClickListener: (muscleGroupId: Long) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(muscleGroupAdapterDelegate(onMuscleGroupClickListener))
    }
}