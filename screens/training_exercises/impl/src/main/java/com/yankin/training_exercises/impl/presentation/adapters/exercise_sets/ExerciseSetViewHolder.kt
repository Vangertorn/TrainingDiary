package com.yankin.training_exercises.impl.presentation.adapters.exercise_sets

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemExerciseSetBinding

internal fun exerciseSetAdapterDelegate(
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<ExerciseSetUiModel, UiItem, ItemExerciseSetBinding>(
        { layoutInflater, parent ->
            ItemExerciseSetBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        bind {
            binding.exerciseSet.text = item.description
        }
    }