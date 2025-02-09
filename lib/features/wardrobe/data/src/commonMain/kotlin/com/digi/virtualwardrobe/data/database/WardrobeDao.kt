package com.digi.virtualwardrobe.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.digi.virtualwardrobe.data.entity.WardrobeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WardrobeDao {
    @Insert
    suspend fun insert(item: WardrobeEntity)

    @Query("SELECT * FROM WardrobeEntity")
    fun getAllAsFlow(): Flow<List<WardrobeEntity>>
}