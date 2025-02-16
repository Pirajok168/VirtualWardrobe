package com.digi.virtualwardrobe.wardrobe.domain.repository

import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.model.WardrobeTypeEntity
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class CreateWardrobeRepositoryImpl(
    private val wardrobeDao: WardrobeDao
): CreateWardrobeRepository {

    private val _currentByteArray = MutableStateFlow<ByteArray?>(null)
    override val currentByteArray: StateFlow<ByteArray?>
        get() = _currentByteArray.asStateFlow()

    override suspend fun onSetCurrentByteArray(byteArray: ByteArray) {
        _currentByteArray.emit(byteArray)
    }

    override suspend fun onCreateWardrobe(
        type: WardrobeType,
        byteArray: ByteArray?,
        description: String?
    ) =
        wardrobeDao.insertWardrobe(WardrobeTypeEntity.valueOf(type.name), byteArray, description)
}