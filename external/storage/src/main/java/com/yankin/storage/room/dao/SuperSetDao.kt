package com.yankin.storage.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.storage.room.entity.SuperSetEntity
import com.yankin.storage.room.entity.info.SuperSetInfoEntity
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

    @Update
    abstract fun updateSuperSet(superSetEntity: SuperSetEntity)

    @Query("SELECT * FROM table_super_set WHERE id==:idSuperSet LIMIT 1")
    abstract fun getSuperSetById(idSuperSet: Long): SuperSetEntity

    @Transaction
    open fun updateSuperSetById(idSuperSet: Long) {
        updateSuperSet(getSuperSetById(idSuperSet))
    }

    @Query("SELECT * FROM table_super_set WHERE visibility ==:flags ")
    abstract fun getSuperSetByVisible(flags: Boolean): List<SuperSetEntity>?

    @Transaction
    open fun deletedSuperSetByVisible() {

        val list = getSuperSetByVisible(false)
        deleteSuperSets(list ?: listOf())
    }

    @Query("SELECT * FROM table_super_set WHERE idTraining == :id AND deleted ==:flags AND visibility==:visibility")
    abstract fun getSuperSetInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean,
        visibility: Boolean
    ): Flow<List<SuperSetInfoEntity>>
}
