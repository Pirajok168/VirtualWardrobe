package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.database.WardrobeDao
import com.digi.virtualwardrobe.data.entity.WardrobeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

class WardrobeRepositoryImpl(
    private val dao: WardrobeDao
) : WardrobeRepository {

    override val wardrobeItems: Flow<List<WardrobeItem>>
        get() = dao.getAllAsFlow().map { it.map { WardrobeItem(it.name) } }

    override suspend fun addWardrobeElem() {
        dao.insert(WardrobeEntity(name = Random.nextInt().toString()))
    }
}