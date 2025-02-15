package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class WardrobeRepositoryImpl(
    private val wardrobeDao: WardrobeDao
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
            Random.nextInt().toString(),
            WardrobeTypeEntity.valueOf(""),
            null,
            null
        )
    }
}