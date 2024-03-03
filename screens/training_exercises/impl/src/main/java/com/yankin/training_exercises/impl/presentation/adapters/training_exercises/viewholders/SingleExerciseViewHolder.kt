package com.yankin.training_exercises.impl.presentation.adapters.training_exercises.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.training_exercises.impl.presentation.adapters.exercise_sets.ExerciseSetsAdapter
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemExerciseListBinding

internal fun singleExerciseAdapterDelegate(
    onTrainingExercisesClick: (id: TrainingExerciseId) -> Unit
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SingleExerciseUiModel, UiItem, ItemExerciseListBinding>(
        { layoutInflater, parent ->
            ItemExerciseListBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val setsAdapter = ExerciseSetsAdapter()
        binding.rvApproachInExerciseItem.adapter = setsAdapter
        binding.root.debounceClick {
            onTrainingExercisesClick.invoke(item.id)
        }

        bindWithPayloads<SingleExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is SingleExerciseUiModel.Payload.ExerciseComment ->
                        binding.tvCommentExerciseExerciseItem.text = payload.value
                    is SingleExerciseUiModel.Payload.ExerciseName ->
                        binding.tvNameExerciseExerciseItem.text = payload.value
                    is SingleExerciseUiModel.Payload.ExerciseSets ->
                        setsAdapter.items = payload.value
                }
            },
            bindAll = {
                binding.tvNameExerciseExerciseItem.text = item.exerciseName.value
                binding.tvCommentExerciseExerciseItem.text = item.exerciseComment.value
                setsAdapter.items = item.exerciseSets.value
            }
        )
    }