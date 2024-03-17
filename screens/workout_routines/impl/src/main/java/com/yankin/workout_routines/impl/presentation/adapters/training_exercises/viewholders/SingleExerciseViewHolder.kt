package com.yankin.workout_routines.impl.presentation.adapters.training_exercises.viewholders

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.yankin.common.debounce.debounceClick
import com.yankin.common.recyclerview.adapter.UiItem
import com.yankin.common.recyclerview.adapter.bindWithPayloads
import com.yankin.workout_routines.impl.presentation.adapters.exercise_sets.ExerciseSetsAdapter
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.SingleExerciseUiModel
import com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models.TrainingBlockId
import com.yankin.trainingdiary.exercise_list.impl.databinding.ItemExerciseListBinding

@SuppressLint("ClickableViewAccessibility")
internal fun singleExerciseAdapterDelegate(
    dragListener: (RecyclerView.ViewHolder) -> Unit,
    onTrainingBlockClick: (id: TrainingBlockId) -> Unit
): AdapterDelegate<List<UiItem>> =
    adapterDelegateViewBinding<SingleExerciseUiModel, UiItem, ItemExerciseListBinding>(
        { layoutInflater, parent ->
            ItemExerciseListBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val setsAdapter = ExerciseSetsAdapter()
        binding.rvApproachInExerciseItem.adapter = setsAdapter
        binding.root.debounceClick {
            onTrainingBlockClick.invoke(item.id)
        }
        binding.ivSwap.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragListener.invoke(this@adapterDelegateViewBinding)
            }
            false
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