package com.yankin.settings.impl.presentation.exercise_pattern_list.adapter

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class ExercisePatternAdapter(
    onExercisePatternClickListener: (exercisePatternId: Long) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(exercisePatternAdapterDelegate(onExercisePatternClickListener))
    }
}