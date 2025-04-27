package com.digi.virtualwardrobe.outfits.domain.repository

import com.digi.virtualwardrobe.outfits.domain.models.Outfit

interface OutfitsRepository {
    suspend fun insertOutfit(outfit: Outfit): Long
}