package com.yankin.approach_create.impl.presentation.adapters.exercises

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class ExerciseAdapter(
    exerciseClickListener: (exerciseId: Long) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(exerciseAdapterDelegate(exerciseClickListener))
    }
}