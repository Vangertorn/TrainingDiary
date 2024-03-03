package com.yankin.training_exercises.impl.presentation.adapters.exercise_sets

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class ExerciseSetsAdapter() : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(exerciseSetAdapterDelegate())
    }
}