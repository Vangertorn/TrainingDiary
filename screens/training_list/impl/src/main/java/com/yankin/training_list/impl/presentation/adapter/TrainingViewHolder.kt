package com.yankin.training_list.impl.presentation.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.trainingdiary.training_list.impl.databinding.ItemTrainingListBinding

private typealias TrainingViewHolder =
    AdapterDelegateViewBindingViewHolder<TrainingUiModel, ItemTrainingListBinding>

internal fun trainingAdapterDelegate(
    onTrainingClick: (trainingId: Long) -> Unit
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<TrainingUiModel, UiItem, ItemTrainingListBinding>(
        { layoutInflater, parent ->
            ItemTrainingListBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.root.debounceClick {
            onTrainingClick.invoke(item.trainingId)
        }

        bindWithPayloads<TrainingUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is TrainingUiModel.Payload.MuscleGroups -> bindMuscleGroups(payload)
                    is TrainingUiModel.Payload.PersonWeight -> bindPersonWeight(payload)
                    is TrainingUiModel.Payload.TrainingComment -> bindTrainingComment(payload)
                    is TrainingUiModel.Payload.TrainingDate ->
                        binding.tvDateTrainingItem.text = payload.value
                }
            },
            bindAll = {
                binding.tvDateTrainingItem.text = item.trainingDate.value
                bindTrainingComment(item.trainingComment)
                bindMuscleGroups(item.muscleGroups)
                bindPersonWeight(item.personWeight)
            }
        )
    }

private fun TrainingViewHolder.bindMuscleGroups(muscleGroups: TrainingUiModel.Payload.MuscleGroups) {
    binding.tvMuscleGroupsTrainingItem.isVisible = muscleGroups.value.isNotEmpty()
    binding.tvMuscleGroupsTrainingItem.text = muscleGroups.value
}

private fun TrainingViewHolder.bindPersonWeight(personWeight: TrainingUiModel.Payload.PersonWeight) {
    binding.tvWeightTrainingItem.isVisible = personWeight.value.isNotEmpty()
    binding.tvWeightTrainingItem.text = personWeight.value
}

private fun TrainingViewHolder.bindTrainingComment(trainingComment: TrainingUiModel.Payload.TrainingComment) {
    binding.tvCommentTrainingItem.isVisible = trainingComment.value.isNotEmpty()
    binding.tvCommentTrainingItem.text = trainingComment.value
}