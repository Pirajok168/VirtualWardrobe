package com.digi.virtualwardrobe.data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class WardrobeDaoImpl(
    private val wardrobeEntityQueries: WardrobeEntityQueries
): WardrobeDao {
    override val wardrobeItems: Flow<List<WardrobeEntity>>
        get() = wardrobeEntityQueries.selectAll().asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun insertWardrobe(
        type: WardrobeTypeEntity,
        byteArray: ByteArray?,
        description: String?
    ) =
        wardrobeEntityQueries.insertWardrobe(null, type, description, byteArray)

    override suspend fun getWardrobe(id: Long) =
        wardrobeEntityQueries.selectWardrobeById(id).executeAsOne()

}