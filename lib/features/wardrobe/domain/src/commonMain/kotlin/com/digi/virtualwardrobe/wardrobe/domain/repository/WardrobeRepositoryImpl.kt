package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.db.OutfitWardrobeDao
import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.Outfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class WardrobeRepositoryImpl(
    private val wardrobeDao: WardrobeDao,
    private val outfitWardrobeDao: OutfitWardrobeDao
) : WardrobeRepository {

    override val wardrobeItems: Flow<List<WardrobeItem>>
        get() = wardrobeDao.wardrobeItems.map {
            it.map {
                WardrobeItem(
                    id = it.id,
                    type = WardrobeType.valueOf(it.type.toString()),
                    byteArray = it.image,
                    description = it.description
                )
            }
        }

    override suspend fun addWardrobeElem() {
        wardrobeDao.insertWardrobe(
            WardrobeTypeEntity.valueOf(""),
            null,
            null
        )
    }

    override fun selectOutfitsByWardrobeId(id: Long): Flow<List<Outfit>> =
        outfitWardrobeDao.selectOutfitsByWardrobeId(id).map { it.map { Outfit(it.id, it.name, it.description, it.image) }  }

    override suspend fun getWardrobe(id: Long): WardrobeItem {
        return wardrobeDao.getWardrobe(id).let {
            WardrobeItem(
                id = it.id,
                type = WardrobeType.valueOf(it.type.toString()),
                byteArray = it.image,
                description = it.description
            )
        }
    }
}