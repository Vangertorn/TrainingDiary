package com.yankin.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yankin.room.entity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SetDao {

    @Insert
    abstract fun insertSet(setEntity: SetEntity): Long

    @Query("DELETE FROM table_set WHERE id == :setId")
    abstract fun deleteSetById(setId: Long)

    @Query("SELECT * FROM table_set WHERE idExercise == :exerciseId")
    abstract fun getSetListStream(exerciseId: Long): Flow<List<SetEntity>?>

    @Query("SELECT * FROM table_set WHERE idExercise == :exerciseId")
    abstract fun getSetList(exerciseId: Long): List<SetEntity>?
}
