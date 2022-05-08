package com.yankin.trainingdiary.screen.exercise_list

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.example.trainingdiary.R
import com.yankin.trainingdiary.models.info.ViewHolderTypes

class ExerciseRecyclerViewAdapterInSuperSet :
    ListAdapter<ViewHolderTypes.ExerciseInfo, ExerciseRecyclerViewAdapterInSuperSet.ExerciseInfoViewHolder>(
        ExerciseInfoDiffCallBack()
    ) {

    class ExerciseInfoDiffCallBack : DiffUtil.ItemCallback<ViewHolderTypes.ExerciseInfo>() {
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
            return oldItem.exercise.deleted == newItem.exercise.deleted &&
                oldItem.exercise.name == newItem.exercise.name &&
                oldItem.exercise.comment == newItem.exercise.comment &&
                oldItem.approaches!!.size == newItem.approaches!!.size
        }
    }

    inner class ExerciseInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvExerciseName =
            itemView.findViewById<TextView>(R.id.tvNameExercise_super_set_item)
        private val tvComment =
            itemView.findViewById<TextView>(R.id.tvCommentExercise_super_set_item)
        private val rvApproach = itemView.findViewById<RecyclerView>(R.id.rvApproachInSuperSetItem)
        private val chipsLayoutManager =
            ChipsLayoutManager.newBuilder(itemView.context).setChildGravity(Gravity.TOP)
                .setScrollingEnabled(false)
                .setGravityResolver { Gravity.CENTER }
                .setRowBreaker { adapterPosition == 6 }
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_VIEW)
                .withLastRow(true)
                .build()
        private val adapter = ApproachRecyclerViewAdapterInExerciseItem()

        init {
            rvApproach.layoutManager = chipsLayoutManager
            rvApproach.adapter = adapter
        }

        fun bind(item: ViewHolderTypes.ExerciseInfo) {

            tvExerciseName.text = item.exercise.name
            tvComment.text = item.exercise.comment
            adapter.submitList(item.approaches)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseInfoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_super_set_exercise, parent, false)
    )

    override fun onBindViewHolder(holder: ExerciseInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
