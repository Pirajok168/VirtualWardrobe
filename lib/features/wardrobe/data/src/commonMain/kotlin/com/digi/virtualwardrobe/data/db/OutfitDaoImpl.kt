package com.digi.virtualwardrobe.data.db

import com.digi.virtualwardrobe.wardrobe.data.OutfitEntity
import com.digi.virtualwardrobe.wardrobe.data.OutfitEntityQueries
import com.digi.virtualwardrobe.wardrobe.data.OutfitWardrobeEntityQueries

class OutfitDaoImpl(
    private val wardrobeEntityQueries: OutfitEntityQueries,
) : OutfitDao {
    override suspend fun insertOutfit(name: String,
                              description: String?,
                              image: ByteArray?,) : Long {
        wardrobeEntityQueries.insertOutfit(null, name, description, image)

        return wardrobeEntityQueries.last_insert_rowid().executeAsOne()
    }
}