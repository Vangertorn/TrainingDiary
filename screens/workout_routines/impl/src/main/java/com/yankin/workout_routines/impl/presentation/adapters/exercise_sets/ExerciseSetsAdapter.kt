package com.yankin.workout_routines.impl.presentation.adapters.exercise_sets

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class ExerciseSetsAdapter() : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(exerciseSetAdapterDelegate())
    }
}