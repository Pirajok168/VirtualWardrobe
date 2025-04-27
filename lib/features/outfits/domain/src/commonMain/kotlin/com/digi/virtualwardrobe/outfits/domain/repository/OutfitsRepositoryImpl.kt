package com.digi.virtualwardrobe.outfits.domain.repository

import com.digi.virtualwardrobe.outfits.data.OutfitDao
import com.digi.virtualwardrobe.outfits.domain.models.Outfit

class OutfitsRepositoryImpl(
    private val outfitDao: OutfitDao
): OutfitsRepository {
    override suspend fun insertOutfit(outfit: Outfit): Long {
        return outfitDao.insertOutfit("test", "123123123", null)
    }
}