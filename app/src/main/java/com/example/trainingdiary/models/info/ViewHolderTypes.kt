package com.example.trainingdiary.models.info


import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.SuperSet


sealed class ViewHolderTypes(@Ignore val viewType: Int) {

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