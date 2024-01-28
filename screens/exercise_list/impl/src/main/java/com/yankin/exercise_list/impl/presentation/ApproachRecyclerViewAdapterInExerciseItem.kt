package com.yankin.exercise_list.impl.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yankin.common.resource_import.CommonRString
import com.yankin.exercise_list.impl.presentation.models.Approach
import com.yankin.trainingdiary.exercise_list.impl.R

class ApproachRecyclerViewAdapterInExerciseItem :
    ListAdapter<Approach, ApproachRecyclerViewAdapterInExerciseItem.ApproachViewHolder>(
        ApproachDiffCallBack()
    ) {

    class ApproachDiffCallBack : DiffUtil.ItemCallback<Approach>() {
        override fun areItemsTheSame(oldItem: Approach, newItem: Approach): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Approach, newItem: Approach): Boolean {
            return oldItem.reoccurrences == newItem.reoccurrences && oldItem.weight == newItem.weight
        }
    }

    inner class ApproachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvApproachInExerciseItem)
        fun bind(item: Approach) {
            tvText.text =
                itemView.context.getString(CommonRString.approach, item.weight, item.reoccurrences)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ApproachViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_approach_exercise, parent, false)
    )

    override fun onBindViewHolder(holder: ApproachViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
