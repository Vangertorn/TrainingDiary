package com.example.trainingdiary.screen.exercise_list

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver
import com.example.trainingdiary.R
import com.example.trainingdiary.models.info.ExerciseInfo
import com.example.trainingdiary.screen.approach_create.ApproachRecyclerViewAdapter

class ExerciseRecyclerViewAdapter(private val onClick: (ExerciseInfo) -> Unit) :
    ListAdapter<ExerciseInfo, ExerciseRecyclerViewAdapter.ExerciseViewHolder>(
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
        private val rvApproach = itemView.findViewById<RecyclerView>(R.id.rvApproachInExerciseItem)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: ExerciseInfo) {
//            val chipsLayoutManager = ChipsLayoutManager.newBuilder().setChildGravity(Gravity.TOP)
//                .setScrollingEnabled(false).setMaxViewsInRow(3).setGravityResolver(
//                    object : IChildGravityResolver {
//                        override fun getItemGravity(p0: Int): Int {
//                            return Gravity.CENTER
//                        }
//                    }).setRowBreaker { adapterPosition == 6 }
//                .setOrientation(ChipsLayoutManager.HORIZONTAL)
//                .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_SPACE)
//                .withLastRow(true)
//                .build()
            val adapter = ApproachRecyclerViewAdapter(onClick = { approach ->
                Unit
            })

            tvExerciseName.text = item.exercise.name
            rvApproach.adapter = adapter
            adapter.submitList(item.approaches)
//            rvApproach.layoutManager = chipsLayoutManager
        }

    }


}

class ExerciseAdapterDiffCallBack : DiffUtil.ItemCallback<ExerciseInfo>() {
    override fun areItemsTheSame(oldItem: ExerciseInfo, newItem: ExerciseInfo): Boolean {
        return oldItem.exercise.id == newItem.exercise.id
    }

    override fun areContentsTheSame(oldItem: ExerciseInfo, newItem: ExerciseInfo): Boolean {
        return oldItem.exercise.name == newItem.exercise.name && oldItem.approaches == newItem.approaches
    }

}