package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.wardrobe.data.OutfitEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.Outfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    val wardrobeItems: Flow<List<WardrobeItem>>

    suspend fun addWardrobeElem()

    suspend fun selectOutfitsByWardrobeId(id: Long): Flow<List<Outfit>>


    suspend fun getWardrobe(id: Long) : WardrobeItem

    suspend fun  insertOutfit(selectedItems: List<WardrobeItem>)
}