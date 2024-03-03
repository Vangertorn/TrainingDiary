package com.yankin.training_exercises.impl.presentation.adapters.training_exercises.viewholders

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.training_exercises.impl.presentation.adapters.superset_exercises.SuperSetExercisesAdapter
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models.TrainingExerciseId
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemSuperSetBinding

internal fun superSetExerciseAdapterDelegate(
    onTrainingExercisesClick: (id: TrainingExerciseId) -> Unit
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SuperSetUiModel, UiItem, ItemSuperSetBinding>(
        { layoutInflater, parent ->
            ItemSuperSetBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val exerciseAdapter = SuperSetExercisesAdapter()
        binding.rvExerciseInfoInSuperSet.adapter = exerciseAdapter
        binding.root.debounceClick {
            onTrainingExercisesClick.invoke(item.id)
        }

        bindWithPayloads<SuperSetUiModel.Payload>(
            bindPayload = { payload ->
                when (payload) {
                    is SuperSetUiModel.Payload.SuperSetExercises -> exerciseAdapter.items = payload.value
                }
            },
            bindAll = {
                exerciseAdapter.items = item.superSetExercises.value
            }
        )
    }