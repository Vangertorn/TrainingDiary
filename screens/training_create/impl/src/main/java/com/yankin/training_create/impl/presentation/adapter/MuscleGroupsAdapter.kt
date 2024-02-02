package com.yankin.training_create.impl.presentation.adapter

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class MuscleGroupsAdapter(
    muscleGroupClickListener: (muscleGroupId: Long) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(muscleGroupAdapterDelegate(muscleGroupClickListener))
    }
}