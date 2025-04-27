package com.digi.virtualwardrobe.outfits.data

class OutfitDaoImpl(
    private val wardrobeEntityQueries: OutfitEntityQueries,
) : OutfitDao {
    override suspend fun insertOutfit(name: String,
                              description: String?,
                              image: ByteArray?,) : Long {
        wardrobeEntityQueries.transaction {
            wardrobeEntityQueries.insertOutfit(null, name, description, image)
        }


        return wardrobeEntityQueries.selectAllOutfit().executeAsList().size.toLong()
    }
}