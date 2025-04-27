package com.digi.virtualwardrobe.data.db

import com.digi.virtualwardrobe.outfits.data.OutfitEntity
import kotlinx.coroutines.flow.Flow

interface OutfitWardrobeDao {

    suspend fun selectOutfitsByWardrobeId(id: Long): Flow<List<OutfitEntity>>

    suspend fun insertOutfitWardrobe(outfitId: Long,  wardrobeId: List<Long>)
}