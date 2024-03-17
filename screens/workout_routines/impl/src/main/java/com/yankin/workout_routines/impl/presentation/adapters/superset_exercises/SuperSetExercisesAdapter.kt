package com.yankin.workout_routines.impl.presentation.adapters.superset_exercises

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class SuperSetExercisesAdapter() : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(superSetExerciseAdapterDelegate())
    }
}