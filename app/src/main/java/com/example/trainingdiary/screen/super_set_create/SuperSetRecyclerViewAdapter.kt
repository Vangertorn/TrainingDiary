package com.example.trainingdiary.screen.super_set_create

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.screen.training_create.MuscleGroupsRecyclerViewAdapter

class SuperSetRecyclerViewAdapter(private val onClick: (Exercise) -> Unit) :
    ListAdapter<Exercise, RecyclerView.ViewHolder>(
        SuperSetAdapterDiffCallBack()
    ) {
     var selectedPositions: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> AddViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false),
                ::onItemClick
            )
            else -> ExerciseViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false),
                ::onItemClick
            )

        }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.name.isEmpty()) {
            0
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is AddViewHolder -> holder.bind(getItem(position), position == selectedPositions)
            else ->(holder as ExerciseViewHolder).bind(getItem(position), position == selectedPositions)
        }
    }

    private fun onItemClick(position: Int) {
        val prevPos = selectedPositions
        selectedPositions = position
        notifyItemChanged(prevPos)
        notifyItemChanged(position)
        onClick(getItem(position))
    }

    inner class ExerciseViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvName)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: Exercise, selected: Boolean) {
            if (selected) {
                tvText.setBackgroundResource(R.drawable.background_item_muscle_groups_selected)
                tvText.setTextColor(Color.WHITE)

            } else {
                tvText.setBackgroundResource(R.drawable.backgound_item_muscle_groups)
                tvText.setTextColor(Color.BLACK)
            }

            tvText.text = item.name
        }
    }

    inner class AddViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvName)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: Exercise, selected: Boolean) {
            if (selected) {
                tvText.setBackgroundResource(R.drawable.background_item_muscle_groups_selected)
                tvText.setTextColor(Color.WHITE)

            } else {
                tvText.setBackgroundResource(R.drawable.backgound_item_muscle_groups)
                tvText.setTextColor(Color.BLACK)
            }

            tvText.text = "Add exercise"
        }
    }

    class SuperSetAdapterDiffCallBack : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.name == newItem.name
        }
    }
}