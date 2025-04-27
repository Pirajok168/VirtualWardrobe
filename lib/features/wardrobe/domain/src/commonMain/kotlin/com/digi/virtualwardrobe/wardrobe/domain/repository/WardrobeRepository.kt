package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.outfits.domain.models.Outfit
import com.digi.virtualwardrobe.wardrobe.domain.models.ReadyOutfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    val wardrobeItems: Flow<List<WardrobeItem>>

    suspend fun addWardrobeElem()

    suspend fun selectOutfitsByWardrobeId(id: Long): Flow<List<ReadyOutfit>>


    suspend fun getWardrobe(id: Long) : WardrobeItem

    suspend fun  insertOutfit(selectedItems: List<WardrobeItem>)
}