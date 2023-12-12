package com.yankin.trainingdiary.screen.training_list.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.models.Training

class TrainingViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val tvDate = itemView.findViewById<TextView>(R.id.tvDate_training_item)
    private val tvMuscleGroups =
        itemView.findViewById<TextView>(R.id.tvMuscleGroups_training_item)
    private val tvComment = itemView.findViewById<TextView>(R.id.tvComment_training_item)
    private val tvWeight = itemView.findViewById<TextView>(R.id.tvWeight_training_item)

    init {
        itemView.setOnClickListener { itemClick(adapterPosition) }
    }

    fun bind(item: Training) {
        tvDate.text = item.date
        if (item.comment.isNullOrBlank()) {
            tvComment.visibility = View.INVISIBLE
        } else {
            tvComment.text = item.comment
        }
        if (item.muscleGroups.isNullOrBlank()) {
            tvMuscleGroups.visibility = View.INVISIBLE
        } else {
            tvMuscleGroups.text = item.muscleGroups
        }
        if (item.weight.isNullOrBlank()) {
            tvWeight.visibility = View.INVISIBLE
        } else {
            tvWeight.text = itemView.context.getString(R.string.weight, item.weight)
        }
    }
}
