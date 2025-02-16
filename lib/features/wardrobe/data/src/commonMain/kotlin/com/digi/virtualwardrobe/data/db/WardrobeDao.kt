package com.digi.virtualwardrobe.data.db

import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import kotlinx.coroutines.flow.Flow

interface WardrobeDao {

    val wardrobeItems: Flow<List<WardrobeEntity>>

    suspend fun insertWardrobe(type: WardrobeTypeEntity, byteArray: ByteArray?, description: String?)

    suspend fun getWardrobe(id: Long) : WardrobeEntity

}