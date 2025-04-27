package com.digi.virtualwardrobe.outfits.data

interface OutfitDao {
    suspend fun insertOutfit(name: String,
                     description: String?,
                     image: ByteArray?,): Long
}