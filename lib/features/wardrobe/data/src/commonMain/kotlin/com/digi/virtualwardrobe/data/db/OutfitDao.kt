package com.digi.virtualwardrobe.data.db

interface OutfitDao {
    suspend fun insertOutfit(name: String,
                     description: String?,
                     image: ByteArray?,): Long
}