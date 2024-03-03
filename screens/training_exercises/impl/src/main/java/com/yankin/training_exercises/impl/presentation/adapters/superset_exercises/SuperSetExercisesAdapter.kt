package com.yankin.training_exercises.impl.presentation.adapters.superset_exercises

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class SuperSetExercisesAdapter() : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(superSetExerciseAdapterDelegate())
    }
}