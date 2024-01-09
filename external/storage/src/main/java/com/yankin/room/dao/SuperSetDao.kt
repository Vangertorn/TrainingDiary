package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yankin.room.entity.SuperSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SuperSetDao {
    @Insert
    abstract fun insertSuperSet(superSetEntity: SuperSetEntity): Long

    @Delete
    abstract fun deleteSuperSets(superSetEntities: List<SuperSetEntity>)

    @Query("SELECT * FROM table_super_set WHERE deleted ==:flags")
    abstract fun getSuperSetByFlags(
        flags: Boolean
    ): List<SuperSetEntity>

    @Query("UPDATE table_super_set SET visibility = :visibility WHERE id = :superSetId")
    abstract fun updateSuperSetVisible(superSetId: Long, visibility: Boolean)

    @Query("SELECT * FROM table_super_set WHERE id==:idSuperSet LIMIT 1")
    abstract fun getSuperSetById(idSuperSet: Long): SuperSetEntity

    @Query("SELECT * FROM table_super_set WHERE visibility ==:flags ")
    abstract fun getSuperSetByVisible(flags: Boolean): List<SuperSetEntity>?

    @Query("DELETE FROM table_super_set WHERE visibility ==:visible")
    abstract fun deleteSuperSetByVisible(visible: Boolean)

    @Query("SELECT * FROM table_super_set WHERE idTraining == :id AND deleted ==:flags AND visibility==:visibility")
    abstract fun getSuperSetListByTrainingIdFlow(
        id: Long,
        flags: Boolean,
        visibility: Boolean
    ): Flow<List<SuperSetEntity>>

    @Query("UPDATE table_super_set SET deleted = :deleted WHERE id = :superSetId")
    abstract fun updateSuperSetDeleted(superSetId: Long, deleted: Boolean)
}
