package com.yankin.workout_routines.impl.presentation.adapters.training_exercises.viewholders

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.workout_routines.impl.presentation.adapters.superset_exercises.SuperSetExercisesAdapter
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.SuperSetUiModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemSuperSetBinding

@SuppressLint("ClickableViewAccessibility")
internal fun superSetExerciseAdapterDelegate(
    dragListener: (RecyclerView.ViewHolder) -> Unit,
    onTrainingBlockClick: (id: TrainingBlockId) -> Unit
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SuperSetUiModel, UiItem, ItemSuperSetBinding>(
        { layoutInflater, parent ->
            ItemSuperSetBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val exerciseAdapter = SuperSetExercisesAdapter()
        binding.rvExerciseInfoInSuperSet.adapter = exerciseAdapter
        binding.root.debounceClick {
            onTrainingBlockClick.invoke(item.id)
        }
        binding.ivSwap.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragListener.invoke(this@adapterDelegateViewBinding)
            }
            false
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