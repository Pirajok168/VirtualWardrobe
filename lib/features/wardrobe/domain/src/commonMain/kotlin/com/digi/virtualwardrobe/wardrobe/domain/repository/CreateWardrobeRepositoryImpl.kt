package com.digi.virtualwardrobe.wardrobe.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class CreateWardrobeRepositoryImpl: CreateWardrobeRepository {

    private val _currentByteArray = MutableStateFlow<ByteArray?>(null)
    override val currentByteArray: StateFlow<ByteArray?>
        get() = _currentByteArray.asStateFlow()

    override suspend fun onSetCurrentByteArray(byteArray: ByteArray) {
        _currentByteArray.emit(byteArray)
    }
}