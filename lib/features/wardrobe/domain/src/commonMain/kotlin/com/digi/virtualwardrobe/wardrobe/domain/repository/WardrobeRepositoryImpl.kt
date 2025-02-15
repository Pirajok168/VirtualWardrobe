package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.db.OutfitWardrobeDao
import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.Outfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WardrobeRepositoryImpl(
    private val wardrobeDao: WardrobeDao,
    private val outfitWardrobeDao: OutfitWardrobeDao
) : WardrobeRepository {

    override val wardrobeItems: Flow<List<WardrobeItem>>
        get() = wardrobeDao.wardrobeItems.map {
            it.map {
                WardrobeItem(
                    it.id,
                    WardrobeType.valueOf(it.type.toString())
                )
            }
        }

    override suspend fun addWardrobeElem() {
        wardrobeDao.insertWardrobe(
            null,
            WardrobeTypeEntity.valueOf(""),
            null,
            null
        )
    }

    override fun selectOutfitsByWardrobeId(id: Long): Flow<List<Outfit>> =
        outfitWardrobeDao.selectOutfitsByWardrobeId(id).map { it.map { Outfit(it.id, it.name, it.description, it.image) }  }
}