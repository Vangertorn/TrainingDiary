package com.example.trainingdiary.screen.exercise_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.models.Exercise
import com.google.android.material.chip.ChipGroup

class ExerciseRecyclerViewAdapter(private val onClick: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseRecyclerViewAdapter.ExerciseViewHolder>(
        ExerciseAdapterDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_list, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    inner class ExerciseViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvExerciseName =
            itemView.findViewById<TextView>(R.id.tvNameExercise_exercise_item)
        val cgApproachGroup = itemView.findViewById<ChipGroup>(R.id.approaches_chipGroup)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: Exercise) {
            tvExerciseName.text = item.name
        }

    }


}

class ExerciseAdapterDiffCallBack : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.name == newItem.name
    }

}