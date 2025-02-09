package com.digi.virtualwardrobe.data.db

import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import kotlinx.coroutines.flow.Flow

interface WardrobeDao {

    val wardrobeItems: Flow<List<WardrobeEntity>>

    fun insertWardrobe(id: Long?, name: String)

}