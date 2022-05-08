package com.yankin.trainingdiary.dao.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.info.SuperSetInfo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SuperSetDao {
    @Insert
    abstract fun insertSuperSet(superSet: SuperSet): Long

    @Delete
    abstract fun deleteSuperSets(superSets: List<SuperSet>)

    @Query("SELECT * FROM table_super_set WHERE deleted ==:flags")
    abstract fun getSuperSetByFlags(
        flags: Boolean
    ): List<SuperSet>

    @Update
    abstract fun updateSuperSet(superSet: SuperSet)

    @Query("SELECT * FROM table_super_set WHERE id==:idSuperSet LIMIT 1")
    abstract fun getSuperSetById(idSuperSet: Long): SuperSet

    @Transaction
    open fun updateSuperSetById(idSuperSet: Long) {
        updateSuperSet(getSuperSetById(idSuperSet))
    }

    @Query("SELECT * FROM table_super_set WHERE visibility ==:flags ")
    abstract fun getSuperSetByVisible(flags: Boolean): List<SuperSet>?

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
    ): Flow<List<SuperSetInfo>>
}
