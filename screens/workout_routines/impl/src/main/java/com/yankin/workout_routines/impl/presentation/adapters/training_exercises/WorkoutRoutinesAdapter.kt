package com.yankin.workout_routines.impl.presentation.adapters.training_exercises

import androidx.recyclerview.widget.RecyclerView
import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.viewholders.singleExerciseAdapterDelegate
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.viewholders.superSetExerciseAdapterDelegate

internal class WorkoutRoutinesAdapter(
    onTrainingBlockClick: (id: TrainingBlockId) -> Unit,
    dragListener: (RecyclerView.ViewHolder) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(
            singleExerciseAdapterDelegate(
                onTrainingBlockClick = onTrainingBlockClick,
                dragListener = dragListener,
            )
        )
        delegatesManager.addDelegate(
            superSetExerciseAdapterDelegate(
                onTrainingBlockClick = onTrainingBlockClick,
                dragListener = dragListener,
            )
        )
    }
}