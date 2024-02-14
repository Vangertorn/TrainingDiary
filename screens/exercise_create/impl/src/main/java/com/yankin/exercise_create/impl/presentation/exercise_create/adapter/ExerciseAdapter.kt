package com.yankin.exercise_create.impl.presentation.exercise_create.adapter

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.viewholders.addExerciseAdapterDelegate
import com.yankin.exercise_create.impl.presentation.exercise_create.adapter.viewholders.exerciseAdapterDelegate

internal class ExerciseAdapter(
    exerciseClickListener: (exerciseId: Int) -> Unit,
    addExerciseClickListener: () -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(exerciseAdapterDelegate(exerciseClickListener))
        delegatesManager.addDelegate(addExerciseAdapterDelegate(addExerciseClickListener))
    }
}