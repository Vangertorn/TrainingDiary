package com.yankin.room.entity.info

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.yankin.room.entity.ApproachEntity
import com.yankin.room.entity.ExerciseEntity
import com.yankin.room.entity.SuperSetEntity

sealed class ViewHolderTypesEntity(@Ignore val viewType: Int) {

    class SuperSetDate(
        val superSetEntity: SuperSetEntity,
        val exercise: List<ExerciseInfo>?
    ) : ViewHolderTypesEntity(0)

    class ExerciseInfo(
        @Embedded
        val exerciseEntity: ExerciseEntity,
        @Relation(
            parentColumn = "id",
            entityColumn = "idExercise"
        )
        val approaches: List<ApproachEntity>?
    ) : ViewHolderTypesEntity(1)
}
