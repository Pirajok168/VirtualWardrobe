package com.digi.virtualwardrobe.data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.digi.virtualwardrobe.outfits.data.OutfitEntity
import com.digi.virtualwardrobe.wardrobe.data.OutfitWardrobeEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class OutfitWardrobeDaoImpl(
    private val wardrobeEntityQueries: OutfitWardrobeEntityQueries,
): OutfitWardrobeDao {
    override suspend fun selectOutfitsByWardrobeId(id: Long): Flow<List<OutfitEntity>> =
        wardrobeEntityQueries.selectOutfitsByWardrobeId(id)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend  fun insertOutfitWardrobe(outfitId: Long, wardrobeId: List<Long>) {
        wardrobeEntityQueries.transaction {
            wardrobeId.forEach {
                wardrobeEntityQueries.insertOutfitWardrobe(null, outfitId, it)
            }
        }
    }
}