package com.yankin.training_list.impl.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yankin.training_list.impl.presentation.Training
import com.yankin.trainingdiary.training_list.impl.R

class TrainingRecyclerViewAdapter(private val onClick: (Training) -> Unit) :
    ListAdapter<Training, TrainingViewHolder>(TrainingAdapterDiffCallBack()) {

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

    class TrainingAdapterDiffCallBack : DiffUtil.ItemCallback<Training>() {

        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem == newItem
        }
    }
}
