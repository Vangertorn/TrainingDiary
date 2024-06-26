package com.yankin.settings.impl.presentation.exercise_autofill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yankin.settings.impl.presentation.ExerciseName
import com.yankin.trainingdiary.settings.impl.R

class ExerciseAutofillRecyclerViewAdapter(private val onClick: (ExerciseName) -> Unit) :
    ListAdapter<ExerciseName, ExerciseAutofillRecyclerViewAdapter.ExerciseAutofillViewHolder>(
        ExerciseAutofillAdapterDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseAutofillViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_select_dialog, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: ExerciseAutofillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    inner class ExerciseAutofillViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvNameExercise = itemView.findViewById<TextView>(R.id.tvName_exercise)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: ExerciseName) {
            tvNameExercise.text = item.nameExercise
        }
    }
}

class ExerciseAutofillAdapterDiffCallBack : DiffUtil.ItemCallback<ExerciseName>() {
    override fun areItemsTheSame(oldItem: ExerciseName, newItem: ExerciseName): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExerciseName, newItem: ExerciseName): Boolean {
        return oldItem.nameExercise == newItem.nameExercise
    }
}
