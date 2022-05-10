package com.yankin.storage.room.entity.info

import androidx.room.Embedded
import androidx.room.Relation
import com.yankin.storage.room.entity.ExerciseEntity
import com.yankin.storage.room.entity.SuperSetEntity

class SuperSetInfoEntity(
    @Embedded
    val superSetEntity: SuperSetEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idSet"
    )
    val exerciseEntity: List<ExerciseEntity>?
)
