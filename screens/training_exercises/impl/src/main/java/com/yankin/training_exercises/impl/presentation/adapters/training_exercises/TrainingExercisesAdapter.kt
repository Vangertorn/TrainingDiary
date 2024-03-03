package com.yankin.training_exercises.impl.presentation.adapters.training_exercises

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExercise
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.viewholders.singleExerciseAdapterDelegate
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.viewholders.superSetExerciseAdapterDelegate

internal class TrainingExercisesAdapter(
    onTrainingExercisesClick: (id: TrainingExerciseId) -> Unit
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(
            SingleExerciseUiModel.VIEW_TYPE, singleExerciseAdapterDelegate(onTrainingExercisesClick)
        )
        delegatesManager.addDelegate(
            SuperSetUiModel.VIEW_TYPE, superSetExerciseAdapterDelegate(onTrainingExercisesClick)
        )
    }

    override fun getItemId(position: Int): Long {
        val itemId = (items[position] as? TrainingExercise)?.id?.value
        return itemId ?: position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when ((items[position] as? TrainingExercise)) {
            is SingleExerciseUiModel -> SingleExerciseUiModel.VIEW_TYPE
            is SuperSetUiModel -> SuperSetUiModel.VIEW_TYPE
            null -> super.getItemViewType(position)
        }
    }
}