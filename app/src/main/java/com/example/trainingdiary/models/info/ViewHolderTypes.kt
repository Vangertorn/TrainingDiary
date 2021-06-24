package com.example.trainingdiary.models.info

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.SuperSet
import com.example.trainingdiary.models.Training
import kotlinx.parcelize.Parcelize

sealed class ViewHolderTypes(@Ignore val viewType: Int) {

    //    class TrainingInfo(
//        @Embedded
//        val training: Training,
//        @Relation(
//            parentColumn = "id",
//            entityColumn = "idTraining"
//        )
//        val exercises: List<Exercise>?
//    )

    class SuperSetDate(
        val superSet: SuperSet,
        val exercise: List<ExerciseInfo>?
    ) : ViewHolderTypes(0)


    class ExerciseInfo(
        @Embedded
        val exercise: Exercise,
        @Relation(
            parentColumn = "id",
            entityColumn = "idExercise"
        )
        val approaches: List<Approach>?
    ) : ViewHolderTypes(1)
}