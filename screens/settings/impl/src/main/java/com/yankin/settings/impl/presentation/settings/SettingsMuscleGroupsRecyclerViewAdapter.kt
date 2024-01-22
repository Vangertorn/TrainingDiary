package com.yankin.settings.impl.presentation.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yankin.settings.impl.presentation.MuscleGroup
import com.yankin.trainingdiary.settings.impl.R

class SettingsMuscleGroupsRecyclerViewAdapter(private val onClick: (MuscleGroup) -> Unit) :
    ListAdapter<MuscleGroup, SettingsMuscleGroupsRecyclerViewAdapter.MuscleGroupViewHolder>(
        MuscleGroupAdapterDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MuscleGroupViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_approach, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: MuscleGroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    inner class MuscleGroupViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvApproach)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: MuscleGroup) {
            tvText.text = item.nameMuscleGroup
        }
    }

    class MuscleGroupAdapterDiffCallBack : DiffUtil.ItemCallback<MuscleGroup>() {
        override fun areItemsTheSame(oldItem: MuscleGroup, newItem: MuscleGroup): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MuscleGroup, newItem: MuscleGroup): Boolean {
            return oldItem.nameMuscleGroup == newItem.nameMuscleGroup
        }
    }
}
