package com.yankin.trainingdiary.screen.super_set_approach_create

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.models.info.ViewHolderTypes

class SuperSetExerciseInfoRecyclerViewAdapter(private val onClick: (ViewHolderTypes.ExerciseInfo) -> Unit) :
    ListAdapter<ViewHolderTypes.ExerciseInfo, SuperSetExerciseInfoRecyclerViewAdapter.ExerciseViewHolder>(
        SuperSetAdapterDiffCallBack()
    ) {
    var selectedPositions: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_set, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            position == selectedPositions
        )
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

        fun bind(item: ViewHolderTypes.ExerciseInfo, selected: Boolean) {
            if (selected) {
                tvText.setBackgroundResource(R.drawable.background_item_muscle_groups_selected)
                tvText.setTextColor(Color.WHITE)
            } else {
                tvText.setBackgroundResource(R.drawable.backgound_item_muscle_groups)
                tvText.setTextColor(Color.BLACK)
            }

            tvText.text = item.exercise.name
        }
    }

    class SuperSetAdapterDiffCallBack : DiffUtil.ItemCallback<ViewHolderTypes.ExerciseInfo>() {
        override fun areItemsTheSame(
            oldItem: ViewHolderTypes.ExerciseInfo,
            newItem: ViewHolderTypes.ExerciseInfo
        ): Boolean {
            return oldItem.exercise.id == newItem.exercise.id
        }

        override fun areContentsTheSame(
            oldItem: ViewHolderTypes.ExerciseInfo,
            newItem: ViewHolderTypes.ExerciseInfo
        ): Boolean {
            return oldItem.exercise.name == newItem.exercise.name &&
                oldItem.exercise.comment == newItem.exercise.comment
        }
    }
}
