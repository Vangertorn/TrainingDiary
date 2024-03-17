package com.yankin.workout_routines.impl.presentation.adapters.superset_exercises

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.workout_routines.impl.presentation.adapters.exercise_sets.ExerciseSetsAdapter
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemSuperSetExerciseBinding

internal fun superSetExerciseAdapterDelegate(
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SuperSetExerciseUiModel, UiItem, ItemSuperSetExerciseBinding>(
        { layoutInflater, parent ->
            ItemSuperSetExerciseBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val setsAdapter = ExerciseSetsAdapter()
        binding.rvSuperSetExerciseSets.adapter = setsAdapter

        bindWithPayloads<SuperSetExerciseUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is SuperSetExerciseUiModel.Payload.ExerciseComment ->
                        binding.tvSuperSetExerciseComment.text = payload.value
                    is SuperSetExerciseUiModel.Payload.ExerciseName ->
                        binding.tvSuperSetExerciseName.text = payload.value
                    is SuperSetExerciseUiModel.Payload.ExerciseSets ->
                        setsAdapter.items = payload.value
                }
            },
            bindAll = {
                binding.tvSuperSetExerciseName.text = item.exerciseName.value
                binding.tvSuperSetExerciseComment.text = item.exerciseComment.value
                setsAdapter.items = item.exerciseSets.value
            }
        )
    }