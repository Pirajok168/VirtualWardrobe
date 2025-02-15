package com.digi.virtualwardrobe.data.db

import com.digi.virtualwardrobe.wardrobe.data.OutfitEntity
import kotlinx.coroutines.flow.Flow

interface OutfitWardrobeDao {

    fun selectOutfitsByWardrobeId(id: Long): Flow<List<OutfitEntity>>
}