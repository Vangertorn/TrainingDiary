package com.yankin.trainingdiary.screen.training_create

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.yankin.trainingdiary.models.MuscleGroup

class MuscleGroupsRecyclerViewAdapter(private val onClick: (MuscleGroup) -> Unit) :
    ListAdapter<MuscleGroup, MuscleGroupsRecyclerViewAdapter.MuscleGroupsViewHolder>(
        MuscleGroupsAdapterDiffCallBack()
    ) {
    var selectedPositions = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MuscleGroupsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_muscle_groups, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: MuscleGroupsViewHolder, position: Int) {
        holder.bind(getItem(position), position in selectedPositions)
    }

    private fun onItemClick(position: Int) {
        if (position in selectedPositions) {
            selectedPositions.remove(position)
        } else {
            selectedPositions.add(position)
        }
        notifyItemChanged(position)
        onClick(getItem(position))
    }

    inner class MuscleGroupsViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvMuscleGroups)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: MuscleGroup, selected: Boolean) {
            if (selected) {
                tvText.setBackgroundResource(R.drawable.background_item_muscle_groups_selected)
                tvText.setTextColor(Color.WHITE)
            } else {
                tvText.setBackgroundResource(R.drawable.backgound_item_muscle_groups)
                tvText.setTextColor(Color.BLACK)
            }

            tvText.text = item.nameMuscleGroup
        }
    }

    class MuscleGroupsAdapterDiffCallBack : DiffUtil.ItemCallback<MuscleGroup>() {
        override fun areItemsTheSame(oldItem: MuscleGroup, newItem: MuscleGroup): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MuscleGroup, newItem: MuscleGroup): Boolean {
            return oldItem.nameMuscleGroup == newItem.nameMuscleGroup
        }
    }
}
