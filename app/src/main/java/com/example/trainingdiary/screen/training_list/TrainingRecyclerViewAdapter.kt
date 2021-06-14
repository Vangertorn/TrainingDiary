package com.example.trainingdiary.screen.training_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.models.Training

class TrainingRecyclerViewAdapter(private val onClick: (Training) -> Unit) :
    ListAdapter<Training, TrainingRecyclerViewAdapter.TrainingViewHolder>(
        TrainingAdapterDiffCallBack()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrainingViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_training_list, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    inner class TrainingViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate_training_item)
        private val tvMuscleGroups =
            itemView.findViewById<TextView>(R.id.tvMuscleGroups_training_item)
        private val tvComment = itemView.findViewById<TextView>(R.id.tvComment_training_item)
        private val tvWeight = itemView.findViewById<TextView>(R.id.tvWeight_training_item)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: Training) {
            tvDate.text = item.date
            if (item.comment.isNullOrBlank()) {
                tvComment.visibility = View.GONE
            } else {
                tvComment.text = item.comment
            }
            if (item.muscleGroups.isNullOrBlank()) {
                tvMuscleGroups.visibility = View.GONE
            } else {
                tvMuscleGroups.text = item.muscleGroups
            }
            if (item.weight.isNullOrBlank()) {
                tvWeight.visibility = View.GONE
            } else {
                tvWeight.text = "${item.weight} kg"
            }

        }


    }

    class TrainingAdapterDiffCallBack : DiffUtil.ItemCallback<Training>() {
        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.date == newItem.date && oldItem.comment == newItem.comment && oldItem.muscleGroups == newItem.muscleGroups && oldItem.weight == newItem.weight
        }

    }

}