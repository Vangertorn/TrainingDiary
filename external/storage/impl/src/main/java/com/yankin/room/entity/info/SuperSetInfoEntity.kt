package com.yankin.room.entity.info

import androidx.room.Embedded
import androidx.room.Relation
import com.yankin.room.entity.ExerciseEntity
import com.yankin.room.entity.SuperSetEntity

class SuperSetInfoEntity(
    @Embedded
    val superSetEntity: SuperSetEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idSet"
    )
    val exerciseEntity: List<ExerciseEntity>?
)
