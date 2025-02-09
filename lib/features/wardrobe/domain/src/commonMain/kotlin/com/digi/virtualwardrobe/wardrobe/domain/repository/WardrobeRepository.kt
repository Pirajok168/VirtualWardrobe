package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    val wardrobeItems: Flow<List<WardrobeItem>>

    suspend fun addWardrobeElem()
}