package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntityQueries
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

class WardrobeRepositoryImpl(
    private val wardrobeDao: WardrobeDao
) : WardrobeRepository {

    override val wardrobeItems: Flow<List<WardrobeItem>>
        get() = wardrobeDao.wardrobeItems.map { it.map { WardrobeItem(it.name) } }

    override suspend fun addWardrobeElem() {
        wardrobeDao.insertWardrobe(null,  Random.nextInt().toString())
    }
}